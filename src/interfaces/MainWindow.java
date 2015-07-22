package interfaces;

import app_logic.LogicMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Piotr on 2015-03-15.
 */
public class MainWindow extends JFrame implements ActionListener
{
    //text fields
    JTextField textfield_rozmiarPopulacji;
    JTextField textfield_prawdopodobienstwoKrzyzowania;
    JTextField textfield_prawdopodobienstwoMutacji;
    JTextField textfield_liczbaIteracji;
    JTextField textfield_ileElity;

    //labels
    JLabel label_rozmiarPopulacji;
    JLabel label_prawdopodobienstwoKrzyzowania;
    JLabel label_prawdopodobienstwoMutacji;
    JLabel label_liczbaIteracji;
    JLabel label_ileElity;

    //textarea
    JScrollPane scrollPane_areaHolder;
    JTextArea textArea_reportHolder;

    //buttons
    JButton button_run;

    //panels
    JPanel panel_main;

    //logika aplikacji
    LogicMain logic;

    public MainWindow(LogicMain l)
    {
        this.setTitle("Problem komiwojażera - algorytm genetyczny");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.logic = l;

        //rozmiar okna
        Dimension dim_prefferedWindowSize = new Dimension(600, 600);
        this.setPreferredSize(dim_prefferedWindowSize);
        this.setMinimumSize(dim_prefferedWindowSize);
        this.setMaximumSize(dim_prefferedWindowSize);
        this.setResizable(false);

        //polozenie okna
        Dimension dim_screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim_screenSize.width / 2 - this.getSize().width / 2, dim_screenSize.height / 2 - this.getSize().height/2);

        //inicjalizacja kontrolek
        this.initialize_controls();

        //textarea
        Dimension dim_scrollPaneSize = new Dimension(200, 300);
        scrollPane_areaHolder = new JScrollPane(textArea_reportHolder);
        scrollPane_areaHolder.setPreferredSize(dim_scrollPaneSize);

        panel_main = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        panel_main.add(label_rozmiarPopulacji, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        panel_main.add(textfield_rozmiarPopulacji, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        panel_main.add(label_prawdopodobienstwoKrzyzowania, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        panel_main.add(textfield_prawdopodobienstwoKrzyzowania, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        panel_main.add(label_prawdopodobienstwoMutacji, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        panel_main.add(textfield_prawdopodobienstwoMutacji, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        panel_main.add(label_liczbaIteracji, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        panel_main.add(textfield_liczbaIteracji, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        panel_main.add(label_ileElity, gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        panel_main.add(textfield_ileElity, gbc);

        //dodawanie textarea
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 6;
        gbc.gridheight = 6;
        panel_main.add(scrollPane_areaHolder, gbc);

        //inicjalizacja buttona
        Dimension dim_button_size = new Dimension(100, 20);
        button_run = new JButton("Run");
        button_run.setPreferredSize(dim_button_size);
        button_run.addActionListener(this);

        gbc.gridx = 3;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        panel_main.add(button_run, gbc);

        this.add(panel_main);
        this.setVisible(true);
    }//public MainWindow()

    private void initialize_controls()
    {
        //inicjalizacja labelow
        label_liczbaIteracji = new JLabel("Liczba iteracji: ");
        label_prawdopodobienstwoKrzyzowania = new JLabel("Prawdopodobieństwo krzyżowania (%): ");
        label_prawdopodobienstwoMutacji = new JLabel("Prawdopodobieństwo mutacji (%): ");
        label_rozmiarPopulacji = new JLabel("Rozmiar populacji: ");
        label_ileElity = new JLabel("Ilość 'elity': ");

        //inicjalizacja textfieldow
        //formatowanie
        Dimension dim = new Dimension(100, 20);
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        DecimalFormat decimalFormatPercent = (DecimalFormat) numberFormat;
        decimalFormatPercent.applyPattern("##");
        decimalFormatPercent.setMaximumIntegerDigits(2);
        NumberFormat numberFormat2 = NumberFormat.getNumberInstance();
        DecimalFormat decimalFormatStandard = (DecimalFormat) numberFormat2;
        decimalFormatStandard.setMinimumIntegerDigits(1);
        decimalFormatStandard.setMaximumFractionDigits(0);
        //inicjalizacja wlasciwa
        textfield_rozmiarPopulacji = new JTextField();
        textfield_rozmiarPopulacji.setText("50");
        textfield_prawdopodobienstwoKrzyzowania = new JTextField();
        textfield_prawdopodobienstwoKrzyzowania.setText("80");
        textfield_prawdopodobienstwoMutacji = new JTextField();
        textfield_prawdopodobienstwoMutacji.setText("5");
        textfield_liczbaIteracji = new JTextField();
        textfield_liczbaIteracji.setText("100");
        textfield_ileElity = new JTextField();
        textfield_ileElity.setText("2");
        //ustalenie rozmiaru
        textfield_rozmiarPopulacji.setPreferredSize(dim);
        textfield_prawdopodobienstwoKrzyzowania.setPreferredSize(dim);
        textfield_prawdopodobienstwoMutacji.setPreferredSize(dim);
        textfield_liczbaIteracji.setPreferredSize(dim);
        textfield_ileElity.setPreferredSize(dim);

        //inicjalizacja textarea
        Dimension dim_textArea = new Dimension(200, 200);
        textArea_reportHolder = new JTextArea();
        //textArea_reportHolder.setPreferredSize(dim_textArea);
        textArea_reportHolder.setLineWrap(true);
        textArea_reportHolder.setFont(new Font("SansSerif", Font.PLAIN, 14));

    } //private void initialize_controls()

    @Override
    public void actionPerformed(ActionEvent e)
    {
        int _i_liczba_iteracji = Integer.parseInt(textfield_liczbaIteracji.getText());
        int _i_rozmiar_populacji = Integer.parseInt(textfield_rozmiarPopulacji.getText());
        double _d_prawdopodobienstwo_krzyzowania = Double.parseDouble(textfield_prawdopodobienstwoKrzyzowania.getText()) / 100.0;
        double _d_prawdopodobienstwo_mutacji = Double.parseDouble(textfield_prawdopodobienstwoMutacji.getText()) / 100.0;
        int _i_ile_elity = Integer.parseInt(textfield_ileElity.getText());

        logic.run(_i_rozmiar_populacji, _d_prawdopodobienstwo_krzyzowania, _d_prawdopodobienstwo_mutacji, _i_liczba_iteracji, _i_ile_elity, true);
        textArea_reportHolder.setText(logic.getReportString());


    } //public void actionPerformed(ActionEvent e)

    //TODO validate text areas

} //public class MainWindow extends JFrame implements ActionListener

