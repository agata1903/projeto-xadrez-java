package xadrez.pecas;

import jogotabuleiro.Posicao;
import jogotabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

	private PartidaXadrez partidaXadrez;

	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0);

		if (getCor() == Cor.BRANCO) {
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().temPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().temPeca(p) && getTabuleiro().existePosicao(p2) && !getTabuleiro().temPeca(p2) && getContadorMovimento() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().existePosicao(p) && haPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}			
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().existePosicao(p) && haPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}	
			
			// #specialmove en passant white
			if (posicao.getLinha() == 3) {
				Posicao left = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().existePosicao(left) && haPecaOponente(left) && getTabuleiro().peca(left) == partidaXadrez.getvulneravelEnPassant()) {
					mat[left.getLinha() - 1][left.getColuna()] = true;
				}
				Posicao right = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().existePosicao(right) && haPecaOponente(right) && getTabuleiro().peca(right) == partidaXadrez.getvulneravelEnPassant()) {
					mat[right.getLinha() - 1][right.getColuna()] = true;
				}
			}
		}
		else {
			p.setValores(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().temPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().temPeca(p) && getTabuleiro().existePosicao(p2) && !getTabuleiro().temPeca(p2) && getContadorMovimento() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().existePosicao(p) && haPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}			
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().existePosicao(p) && haPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			// #specialmove en passant black
			if (posicao.getLinha() == 4) {
				Posicao left = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().existePosicao(left) && haPecaOponente(left) && getTabuleiro().peca(left) == partidaXadrez.getvulneravelEnPassant()) {
					mat[left.getLinha() + 1][left.getColuna()] = true;
				}
				Posicao right = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().existePosicao(right) && haPecaOponente(right) && getTabuleiro().peca(right) == partidaXadrez.getvulneravelEnPassant()) {
					mat[right.getLinha() + 1][right.getColuna()] = true;
				}
			}			
		}
		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	}
	
}