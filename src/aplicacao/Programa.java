package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import jogotabuleiro.ExcecaoTabuleiro;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class Programa {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partida = new PartidaXadrez();
		List<PecaXadrez> capturadas = new ArrayList<>();
		
		while(!partida.getXequeMate()) {
			try {
				IU.clearScreen();
				IU.imprimePartida(partida, capturadas);
				System.out.println();
				System.out.println("Origem: ");
				PosicaoXadrez origem = IU.lerPosicaoXadrez(sc);
				
				boolean[][] movimentosPossiveis = partida.movimentosPossiveis(origem);
				IU.clearScreen();
				IU.imprimeTabuleiro(partida.getPecas(), movimentosPossiveis);
				System.out.println();
				System.out.println("Destino: ");
				PosicaoXadrez destino = IU.lerPosicaoXadrez(sc);
				
				PecaXadrez pecasCapturadas = partida.movimentoXadrez(origem, destino);
				
				if(pecasCapturadas != null) {
					capturadas.add(pecasCapturadas);
				}
			}
			catch(ExcecaoTabuleiro e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		IU.clearScreen();
		IU.imprimePartida(partida, capturadas);
	}
}
