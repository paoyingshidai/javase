package com.michael.javase.pattern.extension;

import org.apiguardian.api.API;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.*;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@API(status = EXPERIMENTAL, since = "5.0")
@ArgumentsSource(YamlFileArgumentsProvider.class)
public @interface YamlFileSource {

    /**
     * The Yaml classpath resources to use as the sources of arguments; must not be
     * empty.
     */
    String[] resources();

    /**
     * The encoding to use when reading the JSON files; must be a valid charset.
     *
     * <p>Defaults to {@code "UTF-8"}.
     *
     * @see java.nio.charset.StandardCharsets
     */
    String encoding() default "UTF-8";

    /**
     * the class which you want to deserialize to.
     * @return
     */
    Class targetClass() default Object.class;
}
