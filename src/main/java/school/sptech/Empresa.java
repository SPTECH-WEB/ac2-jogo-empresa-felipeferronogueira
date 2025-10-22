package school.sptech;

import school.sptech.exception.ArgumentoInvalidoException;
import school.sptech.exception.JogoInvalidoException;
import school.sptech.exception.JogoNaoEncontradoException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Empresa {

    private String nome;
    private List<Jogo> jogos;

    public Empresa() {
        this.jogos = new ArrayList<>();
    }

    public void adicionarJogo(Jogo jogo) {
        if (jogo == null) {
            throw new JogoInvalidoException("Jogo nao pode ser nulo.");
        }
        if (jogo.getCodigo() == null || jogo.getCodigo().isBlank()) {
            throw new JogoInvalidoException("Codigo invalido.");
        }
        if (jogo.getNome() == null || jogo.getNome().isBlank()) {
            throw new JogoInvalidoException("Nome invalido.");
        }
        if (jogo.getGenero() == null || jogo.getGenero().isBlank()) {
            throw new JogoInvalidoException("Genero invalido.");
        }
        if (jogo.getPreco() == null || jogo.getPreco() <= 0) {
            throw new JogoInvalidoException("Preço invalido.");
        }
        if (jogo.getAvaliacao() == null || jogo.getAvaliacao() < 0 || jogo.getAvaliacao() > 5) {
            throw new JogoInvalidoException("Avaliaçao invalida.");
        }
        if (jogo.getDataLancamento() == null || jogo.getDataLancamento().isAfter(LocalDate.now())) {
            throw new JogoInvalidoException("Data de lançamento invalida.");
        }

        this.jogos.add(jogo);
    }

    public Jogo buscarJogoPorCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new ArgumentoInvalidoException("Codigo invalido.");
        }

        for (Jogo jogo : jogos) {
            if (jogo.getCodigo().equals(codigo)) {
                return jogo;
            }
        }

        throw new JogoNaoEncontradoException("Jogo nao encontrado.");
    }

    public void removerJogoPorCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new ArgumentoInvalidoException("Codigo invalido.");
        }

        Jogo jogoParaRemover = null;
        for (Jogo jogo : jogos) {
            if (jogo.getCodigo().equals(codigo)) {
                jogoParaRemover = jogo;
                break;
            }
        }

        if (jogoParaRemover == null) {
            throw new JogoNaoEncontradoException("Jogo nao encontrado para remoçao.");
        }

        jogos.remove(jogoParaRemover);
    }

    public Jogo buscarJogoComMelhorAvaliacao() {
        if (jogos.isEmpty()) {
            throw new JogoNaoEncontradoException("Nenhum jogo cadastrado.");
        }

        Jogo melhorJogo = jogos.get(0);
        for (Jogo jogo : jogos) {
            if (jogo.getAvaliacao() > melhorJogo.getAvaliacao()) {
                melhorJogo = jogo;
            } else if (jogo.getAvaliacao().equals(melhorJogo.getAvaliacao())) {
                if (jogo.getDataLancamento().isAfter(melhorJogo.getDataLancamento())) {
                    melhorJogo = jogo;
                }
            }
        }

        return melhorJogo;
    }

    public List<Jogo> buscarJogoPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        if (dataInicio == null || dataFim == null || dataInicio.isAfter(dataFim)) {
            throw new ArgumentoInvalidoException("Periodo invalido.");
        }

        List<Jogo> jogosNoPeriodo = new ArrayList<>();
        for (Jogo jogo : jogos) {
            if (!jogo.getDataLancamento().isBefore(dataInicio) &&
                    !jogo.getDataLancamento().isAfter(dataFim)) {
                jogosNoPeriodo.add(jogo);
            }
        }

        return jogosNoPeriodo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Jogo> getJogos() {
        return jogos;
    }
}
