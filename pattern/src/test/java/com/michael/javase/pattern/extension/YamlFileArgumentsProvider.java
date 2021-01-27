package com.michael.javase.pattern.extension;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.junit.platform.commons.PreconditionViolationException;
import org.junit.platform.commons.util.Preconditions;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 * @author Michael
 */
@Slf4j
class YamlFileArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<YamlFileSource> {

    private final BiFunction<Class<?>, String, InputStream> inputStreamProvider;

    private YamlFileSource annotation;
    private String[] resources;
    private Charset charset;
    private Class targetClass;

    YamlFileArgumentsProvider() {
        this(Class::getResourceAsStream);
    }

    YamlFileArgumentsProvider(BiFunction<Class<?>, String, InputStream> inputStreamProvider) {
        this.inputStreamProvider = inputStreamProvider;
    }

    @Override
    public void accept(YamlFileSource annotation) {
        this.annotation = annotation;
        this.resources = annotation.resources();
        this.targetClass = annotation.targetClass();
        this.charset = getCharsetFrom(annotation);
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Arrays.stream(this.resources)
                .map(resource -> openInputStream(context, resource))
                .map(this::beginParsing)
                .flatMap(this::toStream);
    }

    private InputStream openInputStream(ExtensionContext context, String resource) {
        Preconditions.notBlank(resource, "Classpath resource [" + resource + "] must not be null or blank");
        Class<?> testClass = context.getRequiredTestClass();
        return Preconditions.notNull(inputStreamProvider.apply(testClass, resource),
                () -> "Classpath resource [" + resource + "] does not exist");
    }

    @SneakyThrows
    private List beginParsing(InputStream inputStream) {

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, this.targetClass);
            return objectMapper.readValue(inputStream, javaType);
        } catch (Throwable throwable) {
            log.error("parsing yaml file error, error: {}", throwable.getMessage());
        }
        return new ArrayList(0);
    }

    @SneakyThrows
    private Stream<Arguments> toStream(List input) {
        return input.stream().map(Arguments::arguments);
    }

    private Charset getCharsetFrom(YamlFileSource annotation) {
        try {
            return Charset.forName(annotation.encoding());
        } catch (Exception ex) {
            throw new PreconditionViolationException("The charset supplied in " + annotation + " is invalid", ex);
        }
    }
}
