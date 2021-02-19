package com.ticketlog.server.service;

import java.util.Arrays;
import java.util.List;

import com.ticketlog.server.dao.EstadoDao;
import com.ticketlog.server.exception.ApiRequestException;
import com.ticketlog.server.model.Cidade;
import com.ticketlog.server.model.Estado;
import com.ticketlog.server.model.Estado.UF;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class EstadoService {

    private EstadoDao estadoDao;
    private RestTemplate restTemplate;
    private Dotenv dotenv;
    private String apiCidade;

    @Autowired
    public EstadoService(EstadoDao estadoDao, RestTemplate restTemplate) {
        dotenv = Dotenv.configure().ignoreIfMissing().load();
        this.apiCidade = dotenv.get("API_CIDADE_URL");
        this.estadoDao = estadoDao;
        this.restTemplate = restTemplate;
    }

    public Estado saveEstado(Estado estado) {
        try {
            return estadoDao.insertEstado(estado);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiRequestException("Estado não pode ser salvo", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public List<Estado> getAllEstados() {
        try {
            return estadoDao.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiRequestException("Listagem dos estados não pode ser efetuada",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Estado getEstadoById(String estadoUf) {
        try {
            UF estadoId = UF.valueOf(estadoUf.toUpperCase());
            Estado estado = estadoDao.getEstado(estadoId);
            if (estado == null)
                throw new RuntimeException("Estado não encontrado");

            ResponseEntity<Cidade[]> response = restTemplate.getForEntity(this.apiCidade + "/" + estadoUf,
                    Cidade[].class);
            List<Cidade> cList = Arrays.asList(response.getBody());

            long populacao = 0;
            double custo = 0;
            for (Cidade c : cList){
                populacao += c.getPopulacao();
                custo += c.getCustoCidadeUs();
            }               
            estado.setCustoEstadoUs(custo);
            estado.setPopulacao(populacao);
            return estadoDao.insertEstado(estado);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new ApiRequestException("Formato de id invalido", HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new ApiRequestException(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiRequestException("Erro ao consultar estado", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public boolean deleteEstadoById(String estadoUf) {
        try {
            UF estadoId = UF.valueOf(estadoUf.toUpperCase());
            boolean ok = estadoDao.deleteEstadoById(estadoId);
            if (!ok)
                throw new ApiRequestException("Estado não encontrado", HttpStatus.NOT_FOUND);
            return ok;
        } catch (IllegalArgumentException e) {
            throw new ApiRequestException("Formato de id invalido", HttpStatus.BAD_REQUEST);
        }
    }

    public Estado updateEstado(String id, Estado estado) {
        try {
            UF estadoId = UF.valueOf(id.toUpperCase());
            return estadoDao.updateEstado(estadoId, estado);
        } catch (IllegalArgumentException e) {
            throw new ApiRequestException("Formato de id invalido", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiRequestException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}