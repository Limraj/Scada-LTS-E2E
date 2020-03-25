package org.scadalts.e2e.test.impl.config.auto.registers;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.Command;

import java.util.*;

@Log4j2
public class CriteriaRegister implements AutoCloseable {

    private Map<Class<?>, Set<?>> criterias = new HashMap<>();
    private final Class<?> tagetClass;

    public CriteriaRegister(Class<?> tagetClass) {
        this.tagetClass = tagetClass;
    }

    public <T extends CriteriaObject> void register(Class<T> clazz, Set<T> sets) {
        criterias.computeIfAbsent(clazz, a -> criterias.put(a, new HashSet<>()));
        ((Set<T>)criterias.get(clazz)).addAll(sets);
    }

    public <T extends CriteriaObject> void register(Class<T> clazz, T object) {
        criterias.computeIfAbsent(clazz, a -> criterias.put(a, new HashSet<>()));
        ((Set<T>)criterias.get(clazz)).add(object);
    }

    public <T extends CriteriaObject> Set<T> get(Class<T> clazz) {
        return Set.class.<T>cast(criterias.get(clazz));
    }

    public <T extends CriteriaObject> Set<Class<T>> keySet() {
        return Set.class.<T>cast(criterias.keySet());
    }

    public void merge(CriteriaRegister criteriaRegister) {
        criteriaRegister.keySet()
                .stream()
                .peek(key -> {
                        if(criterias.containsKey(key)) {
                            this.get(key).addAll(criteriaRegister.get(key));
                        } else {
                            this.register(key, criteriaRegister.get(key));
                        }
                    })
                .close();
    }

    @Override
    public void close() {
        criterias = Collections.unmodifiableMap(criterias);
        CriteriaRegisterAggregator criteriaRegisterAggregator = CriteriaRegisterAggregator.INSTANCE;
        criteriaRegisterAggregator.putRegister(tagetClass, this);
    }

    public void clear() {
        criterias.clear();
    }

    public static CriteriaRegister getRegister(Class<?> classTarget, Command<?> executedIfNotExists) {
        CriteriaRegisterAggregator criteriaRegisterAggregator = CriteriaRegisterAggregator.INSTANCE;

        if(!criteriaRegisterAggregator.containsRegister(classTarget)) {
            logger.info("run... {}", executedIfNotExists.getClass().getSimpleName());
            executedIfNotExists.execute();
        }

        return criteriaRegisterAggregator.getRegister(classTarget);
    }

    public static CriteriaRegister removeRegister(Class<?> classTarget) {
        CriteriaRegisterAggregator criteriaRegisterAggregator = CriteriaRegisterAggregator.INSTANCE;
        return criteriaRegisterAggregator.removeRegister(classTarget);
    }

}
