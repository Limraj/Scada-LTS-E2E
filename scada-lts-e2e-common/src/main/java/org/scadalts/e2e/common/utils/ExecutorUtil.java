package org.scadalts.e2e.common.utils;

import lombok.extern.log4j.Log4j2;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Log4j2
public class ExecutorUtil {

    public static <T, U, S, R, E extends Throwable> R execute(FunctionlInterfaces.TriFunction<T, U, S, R> triFunction, T arg1, U arg2,
                                                              S arg3, Function<Throwable, E> exception) throws E {
        try {
            return triFunction.apply(arg1, arg2, arg3);
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(), throwable);
            throw exception.apply(throwable);
        }
    }

    public static <T, U, R, E extends Throwable> R execute(BiFunction<T, U, R> biFunction, T arg1, U arg2,
                                      Function<Throwable, E> exception) throws E {
        try {
            return biFunction.apply(arg1, arg2);
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(), throwable);
            throw exception.apply(throwable);
        }
    }

    public static <T, R, E extends Throwable> R execute(Function<T, R> function, T arg,
                                   Function<Throwable, E> exception) throws E {
        try {
            return function.apply(arg);
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(), throwable);
            throw exception.apply(throwable);
        }
    }

    public static <R, E extends Throwable> R execute(Supplier<R> supplier,
                                                     Function<Throwable, E> exception) throws E {
        try {
            return supplier.get();
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(), throwable);
            throw exception.apply(throwable);
        }
    }

    public static <T, E extends Throwable> void executeConsumer(Consumer<T> consumer, T arg,
                                   Function<Throwable, E> exception) throws E {
        try {
            consumer.accept(arg);
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(), throwable);
            throw exception.apply(throwable);
        }
    }

    public static <T, E extends Throwable> void executeConsumerThrowable(FunctionlInterfaces.ConsumerThrowable<T> consumer, T arg,
                                                                         Function<Throwable, E> exception) throws E {
        try {
            consumer.accept(arg);
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(), throwable);
            throw exception.apply(throwable);
        }
    }

    public static <E extends Throwable, K> void executeConsumer(FunctionlInterfaces.Executable executor,
                                                                     Consumer<K> execueIfException, K arg,
                                                                     Function<Throwable, E> exception) throws E {
        try {
            executor.execute();
        } catch (Throwable throwable) {
            execueIfException.accept(arg);
            throw exception.apply(throwable);
        }
    }

    public static <E extends Throwable, K> void execute(FunctionlInterfaces.Executable executor,
                                                                Function<Throwable, E> exception) throws E {
        try {
            executor.execute();
        } catch (Throwable throwable) {
            throw exception.apply(throwable);
        }
    }

    public static <E extends Throwable, K, R> void executeFunction(FunctionlInterfaces.Executable executor,
                                                        Function<K, R> executeIfException, K arg,
                                                        Function<Throwable, E> exception) throws E {
        try {
            executor.execute();
        } catch (Throwable throwable) {
            try {
                executeIfException.apply(arg);
            } catch (Throwable throwable1) {
                logger.error(throwable1.getMessage(), throwable1);
            }
            throw exception.apply(throwable);
        }
    }

    public static <E extends Throwable, K, R> void executeBiFunction(FunctionlInterfaces.Executable executor,
                                                                     Function<K, R> executeIfException, K arg,
                                                                     BiFunction<String, Throwable, E> exception) throws E {
        try {
            executor.execute();
        } catch (Throwable throwable) {
            try {
                executeIfException.apply(arg);
            } catch (Throwable throwable1) {
                throw exception.apply(throwable1.getMessage(), throwable1);
            }
            throw exception.apply(throwable.getMessage(), throwable);
        }
    }
}
