package xadrez.pecas;

import jogotabuleiro.Posicao;
import jogotabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez{
	
	private PartidaXadrez partidaXadrez;

	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean [][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0,0);
		
		if(getCor() == Cor.BRANCO) {
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());
			if(getTabuleiro().existePosicao(p) && !getTabuleiro().temPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() -1, posicao.getColuna());
			if(getTabuleiro().existePosicao(p) && !getTabuleiro().temPeca(p) && getTabuleiro().existePosicao(p2) && !getTabuleiro().temPeca(p2) && getContadorMovimento() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if(getTabuleiro().existePosicao(p) && !getTabuleiro().temPeca(p) && haPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}

			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if(getTabuleiro().existePosicao(p) && !getTabuleiro().temPeca(p) && haPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			//movimento en passant branco
			if(posicao.getLinha() == 3) {
				Posicao esq = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if(getTabuleiro().existePosicao(esq) && haPecaOponente(esq) && getTabuleiro().peca(esq) == partidaXadrez.getvulneravelEnPassant()) {
					mat[esq.getLinha() - 1][esq.getColuna()] = true;
				}
				Posicao dir = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if(getTabuleiro().existePosicao(dir) && haPecaOponente(dir) && getTabuleiro().peca(dir) == partidaXadrez.getvulneravelEnPassant()) {
					mat[dir.getLinha() - 1][dir.getColuna()] = true;
				}
			}
		}
			else {
					
					p.setValores(posicao.getLinha() + 1, posicao.getColuna());
					if(getTabuleiro().existePosicao(p) && !getTabuleiro().temPeca(p)) {
						mat[p.getLinha()][p.getColuna()] = true;
					}
					p.setValores(posicao.getLinha() + 2, posicao.getColuna());
					Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
					if(getTabuleiro().existePosicao(p) && !getTabuleiro().temPeca(p) && getTabuleiro().existePosicao(p2) && !getTabuleiro().temPeca(p2) && getContadorMovimento() == 0) {
						mat[p.getLinha()][p.getColuna()] = true;
					}
					p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
					if(getTabuleiro().existePosicao(p) && !getTabuleiro().temPeca(p) && haPecaOponente(p)) {
						mat[p.getLinha()][p.getColuna()] = true;
					}
				}
					p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
					if(getTabuleiro().existePosicao(p) && !getTabuleiro().temPeca(p) && haPecaOponente(p)) {
						mat[p.getLinha()][p.getColuna()] = true;
					}
					
					//movimento en passant preto
					if(posicao.getLinha() == 4) {
						Posicao esq = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
						if(getTabuleiro().existePosicao(esq) && haPecaOponente(esq) && getTabuleiro().peca(esq) == partidaXadrez.getvulneravelEnPassant()) {
							mat[esq.getLinha() + 1][esq.getColuna()] = true;
						}
						Posicao dir = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
						if(getTabuleiro().existePosicao(dir) && haPecaOponente(dir) && getTabuleiro().peca(dir) == partidaXadrez.getvulneravelEnPassant()) {
							mat[dir.getLinha() + 1][dir.getColuna()] = true;
						}
					}
		return mat;
	}

	@Override
	public String toString() {
		return "P";
	}
}
