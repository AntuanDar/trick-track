package ru.spb.taranenkoant.trick.track.client.dao;

import ru.spb.taranenkoant.trick.track.client.ConnectionSettings;
import ru.spb.taranenkoant.trick.track.client.utils.ObjectFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Created by taranenko on 21.03.2023
 * description:
 */
public class AppDaoFactory {

    private static AppDaoFactory instance = new AppDaoFactory(new DaoParams(
            ConnectionSettings.DEFAULT_SERVER_URL
    ));

    // ну да, фабрика фабрик, че такого
    private final Map<Class, ObjectFactory> daoFactories = new HashMap<>();
    private DaoParams params;

    AppDaoFactory() {
    }

    public AppDaoFactory(DaoParams params) {
        this.params = params;
        putFactory(LoginDao.class, () -> new LoginDao(this.params));
    }

    public static void setParams(DaoParams params) {
        AppDaoFactory.instance.params = params;
        AppDaoFactory.instance.daoFactories.values().forEach(ObjectFactory::clear);
    }

    public static void setInstance(AppDaoFactory instance) {
        AppDaoFactory.instance = instance;
    }

    protected <T> void putFactory(Class<T> type, Supplier<T> factory) {
        daoFactories.put(type, new ObjectFactory<>(factory));
    }

    private <T> T getProduct(Class<T> type) {
        ObjectFactory factory = daoFactories.get(type);
        return type.cast(factory.get());
    }

    public static LoginDao getLoginDao() {
        return instance.getProduct(LoginDao.class);
    }
}
