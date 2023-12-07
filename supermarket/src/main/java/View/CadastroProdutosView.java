package View;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Controller.CadastroProdutos.ProdutosControl;
import Controller.CadastroProdutos.ProdutosDAO;
import Model.Produtos;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.*;

public class CadastroProdutosView extends JPanel {
    // Atributos

    // JTextField
    private JTextField inputNome;
    private JTextField inputCodigo;
    private JTextField inputQuantidade;
    private JTextField inputPreco;

    // JLabel
    private JLabel labelNome;
    private JLabel labelCodigo;
    private JLabel labelQuantidade;
    private JLabel labelPreco;

    // JButton
    private JButton btnCadastrar;
    private JButton btnApagar;
    private JButton btnAtualizar;

    // JTable - Tabela
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Produtos> produtos = new ArrayList<>();
    private int linhaSelecionada = -1;

    // Construtor
    public CadastroProdutosView() {
        // JPanel - Painéis
        JPanel mainPanel = new JPanel();
        JPanel inputPanel = new JPanel();
        JPanel btnPanel = new JPanel();

        // Layout dos Painéis
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        mainPanel.setLayout(new BorderLayout());

        // Construindo a tabela
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nome");
        tableModel.addColumn("Código");
        tableModel.addColumn("Quantidade");
        tableModel.addColumn("Preço");
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(table);

        // Definindo o tamanho dos JTextField
        inputNome = new JTextField(20);
        inputCodigo = new JTextField(20);
        inputQuantidade = new JTextField(20);
        inputPreco = new JTextField(20);

        // Definindo a escrita dos JLabel
        labelNome = new JLabel("Nome");
        labelCodigo = new JLabel("Codigo");
        labelQuantidade = new JLabel("Quantidade");
        labelPreco = new JLabel("Preço");

        // Definindo os botões JButton
        btnCadastrar = new JButton("Cadastrar");
        btnApagar = new JButton("Apagar");
        btnAtualizar = new JButton("Atualizar");

        // Adicionando os JLabel e os JTextField ao inputPanel
        inputPanel.add(labelNome);
        inputPanel.add(inputNome);
        inputPanel.add(labelCodigo);
        inputPanel.add(inputCodigo);
        inputPanel.add(labelQuantidade);
        inputPanel.add(inputQuantidade);
        inputPanel.add(labelPreco);
        inputPanel.add(inputPreco);

        // Adicionando os JButton ao btnPanel
        btnPanel.add(btnCadastrar);
        btnPanel.add(btnApagar);
        btnPanel.add(btnAtualizar);

        // Definindo o mainPanel
        this.add(mainPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        // Criando o banco de dados
        new ProdutosDAO().criaTabela();

        // atualizando a tabela
        atualizarTabela();

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                linhaSelecionada = table.rowAtPoint(evt.getPoint());
                if (linhaSelecionada != -1) {
                    inputNome.setText((String) table.getValueAt(linhaSelecionada, 0));
                    inputCodigo.setText((String) table.getValueAt(linhaSelecionada, 1));
                    inputQuantidade.setText((String) table.getValueAt(linhaSelecionada, 2));
                    inputPreco.setText((String) table.getValueAt(linhaSelecionada, 3));
                }
            }
        });

        ProdutosControl operacoes = new ProdutosControl(produtos, tableModel, table);

        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });

    }

     private void atualizarTabela() {
            // atualizar tabela pelo banco de dados
            tableModel.setRowCount(0);
            produtos = new ProdutosDAO().listarTodos();
            for (Produtos produto : produtos) {
                tableModel.addRow(new Object[] {produto.getNome(), produto.getCodigo(), produto.getQuantidade(), produto.getPreco()});
            }
    
        }

}
