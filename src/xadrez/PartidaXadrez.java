package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jogotabuleiro.Peca;
import jogotabuleiro.Posicao;
import jogotabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private Tabuleiro tabuleiro;
	private int turno;
	private Cor jogadorAtual;
	private boolean xeque;
	private boolean xequeMate;
	
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();
	
	public int getTurno() {
		return turno;
	}
	
	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8,8);
		turno = 1;
		jogadorAtual = Cor.BRANCO;
		configInicial();
	}
	
	public boolean getXeque() {
		return xeque;
	}
	
	public boolean getXequeMate() {
		return xequeMate;
	}
	
	public PecaXadrez[][]getPecas(){
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for(int i = 0; i < tabuleiro.getLinhas(); i++) {
			for(int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaXadrez)tabuleiro.peca(i, j);
			}
		}
		return mat;
	}
	
	public PecaXadrez movimentoXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.paraPosicao();
		Posicao destino = posicaoDestino.paraPosicao();
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		Peca pecaCapturada = realizarMovimento(origem, destino);
		
		if(testeXeque(jogadorAtual)) {
			desfazerMovimento(origem,destino, pecaCapturada);
			throw new ExcecaoXadrez("Você não pode se colocar em xeque!");
		}
		
		xeque = (testeXeque(oponente(jogadorAtual))) ? true : false;
		
		if(testeXequeMate(oponente(jogadorAtual))) {
			xequeMate = true;
		}
		else {
			proximoTurno();	
		}
		
		return(PecaXadrez)pecaCapturada;
		
	}
	
	public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoOrigem){
		Posicao posicao = posicaoOrigem.paraPosicao();
		validarPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}
	private Peca realizarMovimento(Posicao origem, Posicao destino) {
		PecaXadrez p = (PecaXadrez)tabuleiro.removerPeca(origem);
		p.aumentarContadorMovimento();
		
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, destino);
		
		if(pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		return pecaCapturada;
	}
	
	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaXadrez p = (PecaXadrez)tabuleiro.removerPeca(destino);
		p.diminuirContadorMovimento();
		tabuleiro.colocarPeca(p, origem);
		
		if(pecaCapturada != null) {
			tabuleiro.colocarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
	}
	private void validarPosicaoOrigem(Posicao posicao) {
		if(!tabuleiro.temPeca(posicao)) {
			throw new ExcecaoXadrez("Não há peça na posição de origem!");
		}
		if(jogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new ExcecaoXadrez("A peça escolhida não é sua!");
		}
		if(!tabuleiro.peca(posicao).haMovimentoPossivel()) {
			throw new ExcecaoXadrez("Não existe movimentos possíveis para essa peça");
		}
	}
	
	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if(!tabuleiro.peca(origem).movimentoPossivel(destino)) {
			throw new ExcecaoXadrez("A peça escolhida não pode mover para esta posição!");
		}
	}
	
	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}
	
	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}
	
	private PecaXadrez rei(Cor cor) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x ->((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for(Peca p : list) {
			if(p instanceof Rei) {
				return (PecaXadrez)p;
			}
		}
		throw new IllegalStateException("Não existe rei na cor " + cor);
	}
	
	private boolean testeXeque(Cor cor) {
		Posicao posicaoRei = rei(cor).getPosicaoXadrez().paraPosicao();
		List<Peca> pecasOponente = pecasNoTabuleiro.stream().filter(x ->((PecaXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for(Peca p : pecasOponente) {
			boolean[][] mat = p.movimentosPossiveis();
			if(mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testeXequeMate(Cor cor) {
		if(!testeXeque(cor)) {
			return false;
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x ->((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for(Peca p : list) {
			boolean[][] mat = p.movimentosPossiveis();
			for(int i = 0; i < tabuleiro.getLinhas(); i++) {
				for(int j = 0; j < tabuleiro.getColunas(); j++) {
					if(mat[i][j]) {
						Posicao origem = ((PecaXadrez)p).getPosicaoXadrez().paraPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = realizarMovimento(origem, destino);
						boolean testeXeque = testeXeque(cor);
						desfazerMovimento(origem, destino, pecaCapturada);
						if(!testeXeque) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	private void colocarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
		pecasNoTabuleiro.add(peca);
	}
	private void configInicial() {
		colocarNovaPeca('h', 7, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('d', 1, new Torre(tabuleiro, Cor.BRANCO));
        colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO));

        colocarNovaPeca('b', 8, new Torre(tabuleiro, Cor.PRETO));
        colocarNovaPeca('a', 8, new Rei(tabuleiro, Cor.PRETO));
	}
}
