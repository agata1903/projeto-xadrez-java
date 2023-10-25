package xadrez;

import jogotabuleiro.Peca;
import jogotabuleiro.Posicao;
import jogotabuleiro.Tabuleiro;

public abstract class PecaXadrez extends Peca {

	private Cor cor;
	private int contadorMovimento;

	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public int getContadorMovimento() {
		return contadorMovimento;
	}
	
	public void aumentarContadorMovimento() {
		contadorMovimento++;
	}
	
	public void diminuirContadorMovimento() {
		contadorMovimento--;
	}
	
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.daPosicao(posicao);
	}
	protected boolean haPecaOponente(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
	}
}
