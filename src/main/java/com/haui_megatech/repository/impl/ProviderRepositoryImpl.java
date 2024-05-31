/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.repository.impl;

import com.haui_megatech.ApplicationContext;
import com.haui_megatech.model.Provider;
import com.haui_megatech.repository.ProviderRepository;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

/**
 *
 * @author vieth
 */
public class ProviderRepositoryImpl implements ProviderRepository {

    private final String ABS_DATA_PATH;

    public ProviderRepositoryImpl() {
        ABS_DATA_PATH = new ApplicationContext().ABS_PROVIDERS_DATA_PATH;

        initCounter();
    }

    private void initCounter() {
        ArrayList<Provider> providers = this.getAll();
        if (providers.isEmpty()) {
            Provider.counter = 0;
        } else {
            Provider.counter = providers.getLast().getId();
        }
    }

    private boolean saveToDisk(ArrayList<Provider> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ABS_DATA_PATH)
        )) {
            oos.writeObject(list);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public Optional<Provider> findById(Integer id) {
        return this.getAll()
                .parallelStream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Provider> save(Provider provider) {
        ArrayList<Provider> providers = this.getAll();
        if (provider.getId() != null) {
            int foundIndex = this.findIndexById(provider.getId());
            Provider foundProvider = providers.get(foundIndex);
            update(foundProvider, provider);
            providers.set(foundIndex, foundProvider);
            return this.saveToDisk(providers)
                    ? Optional.of(foundProvider)
                    : Optional.empty();
        }

        provider.setId(++Provider.counter);
        provider.setWhenCreated(new Date());
        provider.setImportBills(new ArrayList<>());
        providers.add(provider);

        return this.saveToDisk(providers)
                ? Optional.of(provider)
                : Optional.empty();
    }

    private int findIndexById(Integer id) {
        ArrayList<Provider> providers = this.getAll();
        for (int i = 0; i < providers.size(); ++i) {
            if (providers.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    private boolean update(Provider oldInfo, Provider newInfo) {
        oldInfo.setName(newInfo.getName());
        oldInfo.setPhoneNumber(newInfo.getPhoneNumber());
        oldInfo.setEmail(newInfo.getEmail());
        oldInfo.setAddress(newInfo.getAddress());
        oldInfo.setLastUpdated(new Date());
        return true;
    }

    @Override
    public ArrayList<Provider> saveAll(ArrayList<Provider> providers) {
        ArrayList<Provider> savedProviders = new ArrayList<>();
        providers.forEach(item -> {
            Optional<Provider> saved = this.save(item);
            if (saved.isPresent()) {
                savedProviders.add(saved.get());
            }
        });
        return savedProviders;
    }

    @Override
    public void deleteById(Integer id) {
        ArrayList<Provider> providers = this.getAll();
        providers.removeIf(item -> item.getId().equals(id));
        this.saveToDisk(providers);
    }

    @Override
    public ArrayList<Provider> getAll() {
        ArrayList<Provider> providers;
        try (ObjectInputStream ois = new ObjectInputStream(
                (new FileInputStream(ABS_DATA_PATH))
        )) {
            providers = (ArrayList<Provider>) ois.readObject();
            if (providers == null) {
                providers = new ArrayList<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList();
        }
        return providers;
    }

}
