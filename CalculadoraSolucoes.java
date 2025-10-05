import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.*;

public class CalculadoraSolucoes extends JFrame {

    private final JTextField display;
    private String operador = "";
    private double primeiroNumero = 0;
    private boolean novoNumero = true;

    private final ArrayList<String> historicoBanners = new ArrayList<>();
    private final ArrayList<JButton> todosBotoes = new ArrayList<>();
    private Color temaAtual = new Color(45, 45, 45);

    public CalculadoraSolucoes() {
        setTitle("Calculadora Soluções");

        // ÍCONE PERSONALIZADO
        ImageIcon icone = new ImageIcon("img/logo.png"); // ou "logo.png" se estiver na raiz
        setIconImage(icone.getImage());

        setSize(500, 630);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        display = new JTextField("0");
        display.setEditable(false);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setFont(new Font("Segoe UI", Font.PLAIN, 36));
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        display.setBackground(Color.WHITE);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelPrincipal.setBackground(temaAtual);
        painelPrincipal.add(display, BorderLayout.NORTH);

        String[][] botoes = {
            {"C", "Banner", "Histórico", "/"},
            {"7", "8", "9", "*"},
            {"4", "5", "6", "-"},
            {"1", "2", "3", "+"},
            {"0", ".", "Tema", "="}
        };

        JPanel painelBotoes = new JPanel(new GridLayout(5, 4, 10, 10));
        painelBotoes.setBackground(temaAtual);

        for (String[] linha : botoes) {
            for (String texto : linha) {
                JButton botao = new JButton(texto);
                botao.setFont(new Font("Segoe UI", Font.BOLD, 20));
                botao.setFocusPainted(false);
                botao.setForeground(Color.WHITE);
                botao.setBackground(temaAtual);
                botao.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                botao.setHorizontalAlignment(SwingConstants.CENTER);
                botao.setHorizontalTextPosition(SwingConstants.CENTER);

                if (texto.equals("Banner") || texto.equals("Histórico")) {
                    botao.setPreferredSize(new Dimension(140, 60));
                }

                botao.addActionListener(this::actionPerformed);
                todosBotoes.add(botao);
                painelBotoes.add(botao);
            }
        }

        painelPrincipal.add(painelBotoes, BorderLayout.CENTER);
        add(painelPrincipal);
        setVisible(true);
    }

    private void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "C" -> {
                display.setText("0");
                operador = "";
                primeiroNumero = 0;
                novoNumero = true;
            }
            case "Banner" -> calcularBanner();
            case "Histórico" -> mostrarHistorico();
            case "Tema" -> escolherCorTema();
            case "=" -> {
                calcular();
                operador = ""
                }
            }
        }
    }

    private void calcular() {
        if (!operador.isEmpty()) {
            double segundoNumero = Double.parseDouble(display.getText());
            double resultado = switch (operador) {
                case "+" -> primeiroNumero + segundoNumero;
                case "-" -> primeiroNumero - segundoNumero;
                case "*" -> primeiroNumero * segundoNumero;
                case "/" -> segundoNumero == 0 ? Double.NaN : primeiroNumero / segundoNumero;
                default -> 0;
            };

            display.setText(Double.isNaN(resultado) ? "Erro" : String.valueOf(resultado));
            primeiroNumero = resultado;
            novoNumero = true;
        }
    }

    private void calcularBanner() {
        String input = JOptionPane.showInputDialog(this,
                "Digite o tamanho do banner em cm (ex: 120x80):",
                "Calcular Banner", JOptionPane.PLAIN_MESSAGE);

        if (input == null || input.isEmpty() || !input.contains("x")) {
            JOptionPane.showMessageDialog(this, "Entrada inválida. Use o formato: largura x altura (em cm).");
            return;
        }

        try {
            String[] partes = input.toLowerCase().replace(" ", "").split("x");
            double largura = Double.parseDouble(partes[0]) / 100.0;
            double altura = Double.parseDouble(partes[1]) / 100.0;
            double area = largura * altura;
            double preco = area * 70.0;

            DecimalFormat df = new DecimalFormat("0.00");
            String resultado = "Banner: " + partes[0] + "x" + partes[1] + " cm = R$ " + df.format(preco);
            historicoBanners.add(resultado);

            JOptionPane.showMessageDialog(this,
                    "Área: " + df.format(area) + " m²\n" +
                    "Valor: R$ " + df.format(preco),
                    "Resultado do Banner",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao calcular. Use o formato correto, como 120x80.");
        }
    }

    private void mostrarHistorico() {
        if (historicoBanners.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum cálculo de banner foi realizado ainda.");
            return;
        }

        JTextArea areaTexto = new JTextArea(10, 30);
        areaTexto.setEditable(false);
        historicoBanners.forEach(entry -> areaTexto.append(entry + "\n"));

        JScrollPane scrollPane = new JScrollPane(areaTexto);
        JOptionPane.showMessageDialog(this, scrollPane, "Histórico de Banners", JOptionPane.INFORMATION_MESSAGE);
    }

    private void escolherCorTema() {
        Color novaCor = JColorChooser.showDialog(this, "Escolha uma cor de tema", temaAtual);
        if (novaCor != null) {
            temaAtual = novaCor;

            for (JButton botao : todosBotoes) {
                botao.setBackground(temaAtual);
                botao.setForeground(Color.WHITE);
            }

            getContentPane().setBackground(temaAtual);
            SwingUtilities.updateComponentTreeUI(this);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalculadoraSolucoes::new);
    }
}

