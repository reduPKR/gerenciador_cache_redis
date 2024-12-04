package br.com.gerenciador.mapper;

public interface Mapper <R, T> {
    R executar(T request);
}
