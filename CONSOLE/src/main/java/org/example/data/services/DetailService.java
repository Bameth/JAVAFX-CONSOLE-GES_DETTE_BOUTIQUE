package org.example.data.services;

import org.example.data.core.interfaces.Service;
import org.example.data.entities.Detail;
public interface DetailService extends Service<Detail> {
    Detail findById(int id);
}
