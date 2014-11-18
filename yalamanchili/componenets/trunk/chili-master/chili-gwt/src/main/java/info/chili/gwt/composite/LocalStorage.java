/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.chili.gwt.composite;

import com.google.gwt.storage.client.Storage;
import com.google.gwt.storage.client.StorageMap;

/**
 *
 * @author ayalamanchili
 */
//TODO move to chili-GWT
public class LocalStorage {

    protected static Storage officeLclStorage = null;
    protected static StorageMap officeMap = null;

    public static void putValue(String key, String value) {
        if (getLocalStorageMap() != null) {
            getLocalStorageMap().put(key, value);
        }
    }

    public static String getValue(String key) {
        if (getLocalStorageMap() != null && getLocalStorageMap().containsKey(key)) {
            return getLocalStorageMap().get(key);
        }
        return null;
    }

    protected static StorageMap getLocalStorageMap() {
        if (officeMap == null && getLocalStorage() != null) {
            officeMap = new StorageMap(getLocalStorage());
        }
        return officeMap;
    }

    protected static Storage getLocalStorage() {
        if (officeLclStorage == null) {
            officeLclStorage = Storage.getLocalStorageIfSupported();
            officeMap = new StorageMap(getLocalStorage());
        }
        return officeLclStorage;
    }

}
