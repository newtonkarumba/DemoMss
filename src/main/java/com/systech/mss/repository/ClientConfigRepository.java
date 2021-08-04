package com.systech.mss.repository;


import com.systech.mss.domain.Config;
import com.systech.mss.domain.common.Clients;

public interface ClientConfigRepository  extends AbstractRepository<Config, Long>{
    Config findClientConfig(Clients clients);
    Config editClientConfig(Clients clients);
}
