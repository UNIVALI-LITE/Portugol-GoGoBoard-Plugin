package br.univali.portugol.plugin.gogoboard.ui.telas;

import br.univali.portugol.nucleo.bibliotecas.base.ErroExecucaoBiblioteca;
import br.univali.portugol.plugin.gogoboard.componetes.DispositivoGoGo;
import br.univali.portugol.plugin.gogoboard.componetes.Motor;
import br.univali.portugol.plugin.gogoboard.componetes.MotorServo;
import br.univali.portugol.plugin.gogoboard.util.JTextFieldLimit;
import br.univali.ps.ui.swing.ColorController;
import br.univali.ps.ui.swing.Themeable;
import br.univali.ps.ui.swing.weblaf.WeblafUtils;
import br.univali.ps.ui.utils.FabricaDicasInterface;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.progressbar.WebProgressBarUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JProgressBar;

/**
 * Classe da janela do monitor de recursos da GoGo Board.
 *
 * @author Ailton Cardoso Jr
 * @version 1.0
 */
public class JanelaMonitor extends javax.swing.JPanel implements Themeable {

    private volatile boolean atualizar = true;
    private Thread threadAtualizaTela;
    private final DispositivoGoGo dispositivoGoGo;
    private ImageIcon iconeMotorEsquerda;
    private ImageIcon iconeMotorEsquerda_lig;
    private ImageIcon iconeMotorEsquerda_sel;
    private ImageIcon iconeMotorEsquerda_sel_lig;
    private ImageIcon iconeMotorDireita;
    private ImageIcon iconeMotorDireita_lig;
    private ImageIcon iconeMotorDireita_sel;
    private ImageIcon iconeMotorDireita_sel_lig;
    private ImageIcon iconeGoGoConectata;
    private ImageIcon iconeGoGoDesconectata;
    private ImageIcon iconeLed_on;
    private ImageIcon iconeLed_off;

    /**
     * Construtor padrão da janela do monitor de recursos da GoGo Board.
     *
     * @param dispositivoGoGo Dispositivo GoGo Board.
     */
    public JanelaMonitor(DispositivoGoGo dispositivoGoGo) {
        initComponents();
        this.dispositivoGoGo = dispositivoGoGo;
        carregarIcones();
        configurarCores();
        criarTooltips();
        criarThread();
    }

    /**
     * Método para carregar os icones do monitor.
     */
    private void carregarIcones() {
        this.iconeMotorEsquerda = getIcone("esquerda");
        this.iconeMotorEsquerda_lig = getIcone("esquerda_lig");
        this.iconeMotorEsquerda_sel = getIcone("esquerda_sel");
        this.iconeMotorEsquerda_sel_lig = getIcone("esquerda_sel_lig");
        this.iconeMotorDireita = getIcone("direita");
        this.iconeMotorDireita_lig = getIcone("direita_lig");
        this.iconeMotorDireita_sel = getIcone("direita_sel");
        this.iconeMotorDireita_sel_lig = getIcone("direita_sel_lig");
        this.iconeGoGoConectata = getIcone("comGoGo");
        this.iconeGoGoDesconectata = getIcone("semGoGo");
        this.iconeLed_on = getIcone("led_on");
        this.iconeLed_off = getIcone("led_off");
    }

    /**
     * Sobrescrita do método para configurar as cores dos elementos da tela.
     *
     * @see Themeable
     */
    @Override
    public void configurarCores() {
        Color[] cores = new Color[]{
            new Color(255, 113, 143), //rosa
            new Color(73, 171, 236), //azul
            new Color(255, 210, 102), //amarelo
            new Color(92, 198, 198), //azul esverdeado
            new Color(162, 116, 255), //roxo
            new Color(255, 168, 82), //laranja
            new Color(146, 229, 62), //verde
            new Color(192, 153, 101), //laranja
        };

        painelPrincipal.setBackground(ColorController.COR_PRINCIPAL);
        painelMotores.setBackground(ColorController.COR_PRINCIPAL);
        painelOutrasAcoes.setBackground(ColorController.COR_PRINCIPAL);
        painelMotorDC.setBackground(ColorController.COR_PRINCIPAL);
        painelMotorServo.setBackground(ColorController.COR_PRINCIPAL);
        painelSensor.setBackground(ColorController.COR_CONSOLE);

        // Cor da aba selecionada
        painelTabMotor.setBackground(ColorController.COR_DESTAQUE);
        painelTabMotor.setSelectedTopBg(ColorController.COR_DESTAQUE);
        // Cor da aba em segundo plano
        painelTabMotor.setBottomBg(ColorController.FUNDO_CLARO);
        painelTabMotor.setTopBg(ColorController.FUNDO_CLARO);
        painelTabMotor.setContentBorderColor(ColorController.FUNDO_CLARO);
        // Cor das letras
        painelTabMotor.setForeground(ColorController.COR_LETRA);
        labelLigarMotor.setForeground(ColorController.COR_LETRA);
        labelDesligarMotor.setForeground(ColorController.COR_LETRA);
        labelDireitaMotor.setForeground(ColorController.COR_LETRA);
        labelReverterMotor.setForeground(ColorController.COR_LETRA);
        labelEsquerdaMotor.setForeground(ColorController.COR_LETRA);
        labelSetForcaMotor.setForeground(ColorController.COR_LETRA);
        labelSensor1.setForeground(ColorController.COR_LETRA);
        labelSensor2.setForeground(ColorController.COR_LETRA);
        labelSensor3.setForeground(ColorController.COR_LETRA);
        labelSensor4.setForeground(ColorController.COR_LETRA);
        labelSensor5.setForeground(ColorController.COR_LETRA);
        labelSensor6.setForeground(ColorController.COR_LETRA);
        labelSensor7.setForeground(ColorController.COR_LETRA);
        labelSensor8.setForeground(ColorController.COR_LETRA);

        // Cor letras botoes dos motores
        labelMotorA.setForeground(ColorController.COR_LETRA);
        labelMotorB.setForeground(ColorController.COR_LETRA);
        labelMotorC.setForeground(ColorController.COR_LETRA);
        labelMotorD.setForeground(ColorController.COR_LETRA);

        // Cor letras botão set posicao servo
        labelSetPosicaoServo.setForeground(ColorController.COR_LETRA);

        // Cor text field set forca motor
        textFieldForcaMotor.setBackground(ColorController.FUNDO_CLARO);
        textFieldForcaMotor.setForeground(ColorController.COR_LETRA);

        // Cor text field set posicao Servo
        textFieldPosicaoServo.setBackground(ColorController.FUNDO_CLARO);
        textFieldPosicaoServo.setForeground(ColorController.COR_LETRA);

        // Cor text field set caracter display
        textFieldSetDisplay.setBackground(ColorController.FUNDO_CLARO);
        textFieldSetDisplay.setForeground(ColorController.COR_LETRA);

        // Cor do slider Forca motor lado esquerdo
        sliderForcaMotor.setProgressTrackBgBottom(ColorController.FUNDO_CLARO);
        sliderForcaMotor.setProgressTrackBgTop(ColorController.FUNDO_CLARO);
        // Cor do slider Forca motor lado direito
        sliderForcaMotor.setTrackBgBottom(ColorController.FUNDO_CLARO);
        sliderForcaMotor.setTrackBgTop(ColorController.FUNDO_CLARO);
        // Cor do pino do slider Forca motor 
        sliderForcaMotor.setThumbBgBottom(ColorController.FUNDO_ESCURO);
        sliderForcaMotor.setThumbBgTop(ColorController.FUNDO_ESCURO);
        sliderForcaMotor.setDrawProgress(false);

        // Cor do slider posição servo lado esquerdo
        sliderPosicaoServo.setProgressTrackBgBottom(ColorController.FUNDO_CLARO);
        sliderPosicaoServo.setProgressTrackBgTop(ColorController.FUNDO_CLARO);
        // Cor do slider posição servo lado direito
        sliderPosicaoServo.setTrackBgBottom(ColorController.FUNDO_CLARO);
        sliderPosicaoServo.setTrackBgTop(ColorController.FUNDO_CLARO);
        // Cor do pino do slider posição servo
        sliderPosicaoServo.setThumbBgBottom(ColorController.FUNDO_ESCURO);
        sliderPosicaoServo.setThumbBgTop(ColorController.FUNDO_ESCURO);
        sliderPosicaoServo.setDrawProgress(false);

        // Cor da label do display de segmentos
        labelTituloDisplay.setBackground(ColorController.COR_DESTAQUE);
        labelTituloDisplay.setForeground(ColorController.COR_LETRA);
        // Cor da label das ações extras
        labelBeep.setForeground(ColorController.COR_LETRA);
        labelLed.setForeground(ColorController.COR_LETRA);
        labelIR.setForeground(ColorController.COR_LETRA);
        labelSetDisplay.setForeground(ColorController.COR_LETRA);

        if (WeblafUtils.weblafEstaInstalado()) {
            // Botoes acao dos motores
            WeblafUtils.configurarBotao(botaoMotorOn, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, 0);
            WeblafUtils.configurarBotao(botaoMotorOff, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, 0);
            WeblafUtils.configurarBotao(botaoMotorEsquerda, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, 0);
            WeblafUtils.configurarBotao(botaoMotorReverte, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, 0);
            WeblafUtils.configurarBotao(botaoMotorDireita, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, 0);
            WeblafUtils.configurarBotao(botaoMotorForca, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, 0);
            // Botão acao servo
            WeblafUtils.configurarBotao(botaoPosicaoServo, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, 0);
            // Botoes motores
            WeblafUtils.configurarToogleBotao(botaoMotorA, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, 0);
            WeblafUtils.configurarToogleBotao(botaoMotorB, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, 0);
            WeblafUtils.configurarToogleBotao(botaoMotorC, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, 0);
            WeblafUtils.configurarToogleBotao(botaoMotorD, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, 0);
            // Botoes outras acoes
            WeblafUtils.configurarBotao(botaoBeep, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, 0);
            WeblafUtils.configurarToogleBotao(botaoLedOn, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, 0);
            WeblafUtils.configurarBotao(botaoSetDisplay, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, ColorController.COR_PRINCIPAL, 0);

            configuraBarrasDeProgresso(cores);
        }
    }

    /**
     * Método para criação dos tool tips para ser exibido na tela.
     *
     * @return ImageIcon
     */
    private void criarTooltips() {
        FabricaDicasInterface.criarTooltip(botaoMotorOn, "Ligar motores selecionados");
        FabricaDicasInterface.criarTooltip(textFieldForcaMotor, "Força do motor");
        FabricaDicasInterface.criarTooltip(textFieldSetDisplay, "Letras/Numeros");
    }

    /**
     * Método para criar a thread de atualização de tela.
     */
    public void criarThread() {
        threadAtualizaTela = new Thread(new Runnable() {
            @Override
            public void run() {
                labelGoGo.setIcon(iconeGoGoConectata);
                while (atualizar && dispositivoGoGo.isConectado()) {
                    try {
                        dispositivoGoGo.atualizarComponetes();
                        int i = 0;
                        for (Component component : painelSensor.getComponents()) {
                            if (component instanceof JProgressBar) {
                                JProgressBar pb = (JProgressBar) component;
                                int valor = dispositivoGoGo.getValorSensor(i, false);
                                pb.setValue(valor);
                                pb.setString(String.valueOf(valor));
                                i++;
                            }
                        }
                        labelIR.setText("Código  = " + dispositivoGoGo.getInfravermelho().getValor(false));
                        Thread.sleep(100);
                    } catch (ErroExecucaoBiblioteca | InterruptedException ex) {
                        Logger.getLogger(JanelaMonitor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                zerarBarraSensores();
                labelGoGo.setIcon(iconeGoGoDesconectata);
            }
        });
    }

    /**
     * Método para iniciar a thread de atualização de tela.
     */
    public void atualizarComponentes() {
        atualizar = true;
        criarThread();
        threadAtualizaTela.start();
    }

    /**
     * Método para interromper a thread da atualização de tela.
     */
    public void interromperThread() {
        atualizar = false;
    }

    /**
     * Método para zerar as barras dos sensores.
     */
    private void zerarBarraSensores() {
        for (Component component : painelSensor.getComponents()) {
            if (component instanceof JProgressBar) {
                JProgressBar pb = ((JProgressBar) component);
                pb.setValue(0);
                pb.setString(String.valueOf(0));
            }
        }
    }

    /**
     * Método para atualizar o status visual dos motores DC.
     */
    private void atualizarStatusBotoesMotoresDC() {
        definirIconeMotores(dispositivoGoGo.getMotorDC('a'), botaoMotorA);
        definirIconeMotores(dispositivoGoGo.getMotorDC('b'), botaoMotorB);
        definirIconeMotores(dispositivoGoGo.getMotorDC('c'), botaoMotorC);
        definirIconeMotores(dispositivoGoGo.getMotorDC('d'), botaoMotorD);
    }

    /**
     * Método para atualizar o status visual dos motores DC.
     */
    private void atualizarStatusBotoesMotoresServo() {
        definirIconeMotores(dispositivoGoGo.getMotorServo('a'), botaoMotorA);
        definirIconeMotores(dispositivoGoGo.getMotorServo('b'), botaoMotorB);
        definirIconeMotores(dispositivoGoGo.getMotorServo('c'), botaoMotorC);
        definirIconeMotores(dispositivoGoGo.getMotorServo('d'), botaoMotorD);
    }

    /**
     * Método para definir o icone do motor DC.
     *
     * @param motorDC Motor correspondente ao botão que terá o icone alterado.
     * @param botao botao correspondente ao motor.
     */
    private void definirIconeMotores(Motor motorDC, WebToggleButton botao) {
        if (motorDC.isLigado()) {
            if (motorDC.isDireita()) {
                if (botao.isSelected()) {
                    botao.setIcon(iconeMotorDireita_sel_lig);
                } else {
                    botao.setIcon(iconeMotorDireita_lig);
                }
            } else {
                if (botao.isSelected()) {
                    botao.setIcon(iconeMotorEsquerda_sel_lig);
                } else {
                    botao.setIcon(iconeMotorEsquerda_lig);
                }
            }
        } else {
            if (motorDC.isDireita()) {
                if (botao.isSelected()) {
                    botao.setIcon(iconeMotorDireita_sel);
                } else {
                    botao.setIcon(iconeMotorDireita);
                }
            } else {
                if (botao.isSelected()) {
                    botao.setIcon(iconeMotorEsquerda_sel);
                } else {
                    botao.setIcon(iconeMotorEsquerda);
                }
            }
        }
    }

    /**
     * Método para carregar o icone da ação.
     *
     * @return ImageIcon
     */
    private ImageIcon getIcone(String nome) {
        try {
            String caminho = "br/univali/portugol/plugin/gogoboard/imagens/monitor/" + nome + ".png";
            Image imagem = ImageIO.read(JanelaMonitor.class.getClassLoader().getResourceAsStream(caminho));
            return new ImageIcon(imagem);
        } catch (IOException ex) {
            System.err.println("Erro ao carregar o icone do plugin na ação Compilar Logo");
            return null;
        }
    }

    /**
     * Método para configurar as barras de progresso dos sensores.
     */
    public void configuraBarrasDeProgresso(Color[] cores) {
        int i = 0;
        for (Component component : painelSensor.getComponents()) {
            if (component instanceof JProgressBar) {
                JProgressBar progressBar = (JProgressBar) component;
                ((WebProgressBarUI) progressBar.getUI()).setProgressTopColor(cores[i]);
                ((WebProgressBarUI) progressBar.getUI()).setProgressBottomColor(cores[i]);
                ((WebProgressBarUI) progressBar.getUI()).setBgBottom(ColorController.COR_CONSOLE);
                ((WebProgressBarUI) progressBar.getUI()).setBgTop(ColorController.COR_CONSOLE);
                ((WebProgressBarUI) progressBar.getUI()).setInnerRound(0);
                ((WebProgressBarUI) progressBar.getUI()).setRound(0);
                ((WebProgressBarUI) progressBar.getUI()).setShadeWidth(0);
                ((WebProgressBarUI) progressBar.getUI()).setIndeterminateBorder(null);
                i++;
            }
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelPrincipal = new javax.swing.JPanel();
        painelSensor = new javax.swing.JPanel();
        progressBarSensor1 = new javax.swing.JProgressBar(progressBarSensor1.VERTICAL);
        progressBarSensor2 = new javax.swing.JProgressBar(progressBarSensor2.VERTICAL);
        progressBarSensor3 = new javax.swing.JProgressBar(progressBarSensor3.VERTICAL);
        progressBarSensor4 = new javax.swing.JProgressBar(progressBarSensor4.VERTICAL);
        progressBarSensor5 = new javax.swing.JProgressBar(progressBarSensor5.VERTICAL);
        progressBarSensor6 = new javax.swing.JProgressBar(progressBarSensor6.VERTICAL);
        progressBarSensor7 = new javax.swing.JProgressBar(progressBarSensor7.VERTICAL);
        progressBarSensor8 = new javax.swing.JProgressBar(progressBarSensor8.VERTICAL);
        labelSensor1 = new javax.swing.JLabel();
        labelSensor2 = new javax.swing.JLabel();
        labelSensor3 = new javax.swing.JLabel();
        labelSensor4 = new javax.swing.JLabel();
        labelSensor5 = new javax.swing.JLabel();
        labelSensor6 = new javax.swing.JLabel();
        labelSensor7 = new javax.swing.JLabel();
        labelSensor8 = new javax.swing.JLabel();
        painelTabMotor = new com.alee.laf.tabbedpane.WebTabbedPane();
        painelMotorDC = new javax.swing.JPanel();
        botaoMotorOn = new com.alee.laf.button.WebButton();
        botaoMotorOff = new com.alee.laf.button.WebButton();
        botaoMotorEsquerda = new com.alee.laf.button.WebButton();
        botaoMotorReverte = new com.alee.laf.button.WebButton();
        botaoMotorDireita = new com.alee.laf.button.WebButton();
        botaoMotorForca = new com.alee.laf.button.WebButton();
        sliderForcaMotor = new com.alee.laf.slider.WebSlider();
        labelLigarMotor = new javax.swing.JLabel();
        labelDesligarMotor = new javax.swing.JLabel();
        labelEsquerdaMotor = new javax.swing.JLabel();
        labelReverterMotor = new javax.swing.JLabel();
        labelDireitaMotor = new javax.swing.JLabel();
        textFieldForcaMotor = new com.alee.laf.text.WebTextField();
        labelSetForcaMotor = new javax.swing.JLabel();
        separadorForcaMotor = new com.alee.laf.separator.WebSeparator();
        painelMotorServo = new javax.swing.JPanel();
        sliderPosicaoServo = new com.alee.laf.slider.WebSlider();
        textFieldPosicaoServo = new com.alee.laf.text.WebTextField();
        separadorPosicaoServo = new com.alee.laf.separator.WebSeparator();
        botaoPosicaoServo = new com.alee.laf.button.WebButton();
        labelSetPosicaoServo = new javax.swing.JLabel();
        painelMotores = new javax.swing.JPanel();
        labelGoGo = new javax.swing.JLabel();
        labelMotorA = new javax.swing.JLabel();
        labelMotorB = new javax.swing.JLabel();
        labelMotorC = new javax.swing.JLabel();
        labelMotorD = new javax.swing.JLabel();
        botaoMotorA = new com.alee.laf.button.WebToggleButton();
        botaoMotorB = new com.alee.laf.button.WebToggleButton();
        botaoMotorC = new com.alee.laf.button.WebToggleButton();
        botaoMotorD = new com.alee.laf.button.WebToggleButton();
        painelOutrasAcoes = new javax.swing.JPanel();
        botaoBeep = new com.alee.laf.button.WebButton();
        labelIconeIR = new javax.swing.JLabel();
        textFieldSetDisplay = new com.alee.laf.text.WebTextField();
        separadorInputDisplay = new com.alee.laf.separator.WebSeparator();
        botaoSetDisplay = new com.alee.laf.button.WebButton();
        labelSetDisplay = new javax.swing.JLabel();
        labelTituloDisplay = new javax.swing.JLabel();
        labelBeep = new javax.swing.JLabel();
        labelLed = new javax.swing.JLabel();
        labelIR = new javax.swing.JLabel();
        botaoLedOn = new com.alee.laf.button.WebToggleButton();

        painelPrincipal.setDoubleBuffered(false);
        painelPrincipal.setFocusable(false);
        painelPrincipal.setPreferredSize(new java.awt.Dimension(1200, 614));

        progressBarSensor1.setMaximum(1024);
        progressBarSensor1.setOrientation(1);
        progressBarSensor1.setBorderPainted(false);
        progressBarSensor1.setEnabled(false);
        progressBarSensor1.setFocusable(false);
        progressBarSensor1.setName(""); // NOI18N
        progressBarSensor1.setRequestFocusEnabled(false);
        progressBarSensor1.setString("0");
        progressBarSensor1.setStringPainted(true);
        progressBarSensor1.setVerifyInputWhenFocusTarget(false);

        progressBarSensor2.setForeground(new java.awt.Color(153, 153, 255));
        progressBarSensor2.setMaximum(1024);
        progressBarSensor2.setOrientation(1);
        progressBarSensor2.setEnabled(false);
        progressBarSensor2.setFocusable(false);
        progressBarSensor2.setName(""); // NOI18N
        progressBarSensor2.setRequestFocusEnabled(false);
        progressBarSensor2.setString("0");
        progressBarSensor2.setStringPainted(true);

        progressBarSensor3.setForeground(new java.awt.Color(153, 153, 255));
        progressBarSensor3.setMaximum(1024);
        progressBarSensor3.setOrientation(1);
        progressBarSensor3.setEnabled(false);
        progressBarSensor3.setFocusable(false);
        progressBarSensor3.setName(""); // NOI18N
        progressBarSensor3.setRequestFocusEnabled(false);
        progressBarSensor3.setString("0");
        progressBarSensor3.setStringPainted(true);

        progressBarSensor4.setForeground(new java.awt.Color(153, 153, 255));
        progressBarSensor4.setMaximum(1024);
        progressBarSensor4.setOrientation(1);
        progressBarSensor4.setEnabled(false);
        progressBarSensor4.setFocusable(false);
        progressBarSensor4.setName(""); // NOI18N
        progressBarSensor4.setRequestFocusEnabled(false);
        progressBarSensor4.setString("0");
        progressBarSensor4.setStringPainted(true);

        progressBarSensor5.setForeground(new java.awt.Color(153, 153, 255));
        progressBarSensor5.setMaximum(1024);
        progressBarSensor5.setOrientation(1);
        progressBarSensor5.setEnabled(false);
        progressBarSensor5.setFocusable(false);
        progressBarSensor5.setName(""); // NOI18N
        progressBarSensor5.setRequestFocusEnabled(false);
        progressBarSensor5.setString("0");
        progressBarSensor5.setStringPainted(true);

        progressBarSensor6.setForeground(new java.awt.Color(153, 153, 255));
        progressBarSensor6.setMaximum(1024);
        progressBarSensor6.setOrientation(1);
        progressBarSensor6.setEnabled(false);
        progressBarSensor6.setFocusable(false);
        progressBarSensor6.setName(""); // NOI18N
        progressBarSensor6.setRequestFocusEnabled(false);
        progressBarSensor6.setString("0");
        progressBarSensor6.setStringPainted(true);

        progressBarSensor7.setForeground(new java.awt.Color(153, 153, 255));
        progressBarSensor7.setMaximum(1024);
        progressBarSensor7.setOrientation(1);
        progressBarSensor7.setEnabled(false);
        progressBarSensor7.setFocusable(false);
        progressBarSensor7.setName(""); // NOI18N
        progressBarSensor7.setRequestFocusEnabled(false);
        progressBarSensor7.setString("0");
        progressBarSensor7.setStringPainted(true);

        progressBarSensor8.setForeground(new java.awt.Color(153, 153, 255));
        progressBarSensor8.setMaximum(1024);
        progressBarSensor8.setOrientation(1);
        progressBarSensor8.setEnabled(false);
        progressBarSensor8.setFocusable(false);
        progressBarSensor8.setName(""); // NOI18N
        progressBarSensor8.setRequestFocusEnabled(false);
        progressBarSensor8.setString("0");
        progressBarSensor8.setStringPainted(true);

        labelSensor1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelSensor1.setText("Sensor 1");

        labelSensor2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelSensor2.setText("Sensor 2");

        labelSensor3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelSensor3.setText("Sensor 3");

        labelSensor4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelSensor4.setText("Sensor 4");

        labelSensor5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelSensor5.setText("Sensor 5");

        labelSensor6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelSensor6.setText("Sensor 6");

        labelSensor7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelSensor7.setText("Sensor 7");

        labelSensor8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelSensor8.setText("Sensor 8");

        javax.swing.GroupLayout painelSensorLayout = new javax.swing.GroupLayout(painelSensor);
        painelSensor.setLayout(painelSensorLayout);
        painelSensorLayout.setHorizontalGroup(
            painelSensorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelSensorLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(labelSensor1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelSensor2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelSensor3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelSensor4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelSensor5, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelSensor6, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelSensor7, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelSensor8, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(painelSensorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(painelSensorLayout.createSequentialGroup()
                    .addGap(27, 27, 27)
                    .addComponent(progressBarSensor1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(progressBarSensor2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(progressBarSensor3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(progressBarSensor4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(progressBarSensor5, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(progressBarSensor6, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(progressBarSensor7, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(progressBarSensor8, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        painelSensorLayout.setVerticalGroup(
            painelSensorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelSensorLayout.createSequentialGroup()
                .addContainerGap(272, Short.MAX_VALUE)
                .addGroup(painelSensorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSensor1)
                    .addComponent(labelSensor2)
                    .addComponent(labelSensor3)
                    .addComponent(labelSensor4)
                    .addComponent(labelSensor5)
                    .addComponent(labelSensor6)
                    .addComponent(labelSensor7)
                    .addComponent(labelSensor8))
                .addContainerGap())
            .addGroup(painelSensorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(painelSensorLayout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addGroup(painelSensorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(progressBarSensor1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(progressBarSensor2, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(progressBarSensor3, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(progressBarSensor4, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(progressBarSensor5, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(progressBarSensor6, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(progressBarSensor7, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(progressBarSensor8, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(35, Short.MAX_VALUE)))
        );

        painelTabMotor.setToolTipText("");
        painelTabMotor.setFont(new java.awt.Font("Dialog", 0, 22)); // NOI18N
        painelTabMotor.setTabbedPaneStyle(com.alee.laf.tabbedpane.TabbedPaneStyle.attached);

        botaoMotorOn.setBorder(null);
        botaoMotorOn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/portugol/plugin/gogoboard/imagens/monitor/liga.png"))); // NOI18N
        botaoMotorOn.setToolTipText("");
        botaoMotorOn.setContentAreaFilled(true);
        botaoMotorOn.setDisabledIcon(null);
        botaoMotorOn.setFocusable(false);
        botaoMotorOn.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        botaoMotorOn.setOpaque(false);
        botaoMotorOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoMotorOnActionPerformed(evt);
            }
        });

        botaoMotorOff.setBorder(null);
        botaoMotorOff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/portugol/plugin/gogoboard/imagens/monitor/desliga.png"))); // NOI18N
        botaoMotorOff.setToolTipText("");
        botaoMotorOff.setContentAreaFilled(true);
        botaoMotorOff.setDisabledIcon(null);
        botaoMotorOff.setFocusable(false);
        botaoMotorOff.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        botaoMotorOff.setOpaque(false);
        botaoMotorOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoMotorOffActionPerformed(evt);
            }
        });

        botaoMotorEsquerda.setBorder(null);
        botaoMotorEsquerda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/portugol/plugin/gogoboard/imagens/monitor/esquerda_sel.png"))); // NOI18N
        botaoMotorEsquerda.setToolTipText("");
        botaoMotorEsquerda.setContentAreaFilled(true);
        botaoMotorEsquerda.setFocusable(false);
        botaoMotorEsquerda.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        botaoMotorEsquerda.setOpaque(false);
        botaoMotorEsquerda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoMotorEsquerdaActionPerformed(evt);
            }
        });

        botaoMotorReverte.setBorder(null);
        botaoMotorReverte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/portugol/plugin/gogoboard/imagens/monitor/reverter.png"))); // NOI18N
        botaoMotorReverte.setToolTipText("");
        botaoMotorReverte.setContentAreaFilled(true);
        botaoMotorReverte.setDisabledIcon(null);
        botaoMotorReverte.setFocusable(false);
        botaoMotorReverte.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        botaoMotorReverte.setOpaque(false);
        botaoMotorReverte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoMotorReverteActionPerformed(evt);
            }
        });

        botaoMotorDireita.setBorder(null);
        botaoMotorDireita.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/portugol/plugin/gogoboard/imagens/monitor/direita_sel.png"))); // NOI18N
        botaoMotorDireita.setToolTipText("");
        botaoMotorDireita.setContentAreaFilled(true);
        botaoMotorDireita.setFocusable(false);
        botaoMotorDireita.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        botaoMotorDireita.setOpaque(false);
        botaoMotorDireita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoMotorDireitaActionPerformed(evt);
            }
        });

        botaoMotorForca.setBorder(null);
        botaoMotorForca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/portugol/plugin/gogoboard/imagens/monitor/ok.png"))); // NOI18N
        botaoMotorForca.setToolTipText("");
        botaoMotorForca.setContentAreaFilled(true);
        botaoMotorForca.setFocusable(false);
        botaoMotorForca.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        botaoMotorForca.setOpaque(false);
        botaoMotorForca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoMotorForcaActionPerformed(evt);
            }
        });

        sliderForcaMotor.setBorder(null);
        sliderForcaMotor.setToolTipText("");
        sliderForcaMotor.setValue(100);
        sliderForcaMotor.setFocusable(false);
        sliderForcaMotor.setTrackShadeWidth(0);
        sliderForcaMotor.setVerifyInputWhenFocusTarget(false);
        sliderForcaMotor.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderForcaMotorStateChanged(evt);
            }
        });

        labelLigarMotor.setText("Ligar");

        labelDesligarMotor.setText("Desligar");

        labelEsquerdaMotor.setText("Esquerda");

        labelReverterMotor.setText("Reverter");

        labelDireitaMotor.setText("Direita");

        textFieldForcaMotor.setForeground(new java.awt.Color(255, 255, 255));
        textFieldForcaMotor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textFieldForcaMotor.setText("100");
        textFieldForcaMotor.setToolTipText("");
        textFieldForcaMotor.setDrawBackground(false);
        textFieldForcaMotor.setDrawBorder(false);
        textFieldForcaMotor.setFont(new java.awt.Font("Dialog", 0, 22)); // NOI18N
        textFieldForcaMotor.setSelectionColor(new java.awt.Color(0, 153, 153));
        textFieldForcaMotor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textFieldForcaMotorKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textFieldForcaMotorKeyTyped(evt);
            }
        });

        labelSetForcaMotor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelSetForcaMotor.setText("Set Força");

        javax.swing.GroupLayout painelMotorDCLayout = new javax.swing.GroupLayout(painelMotorDC);
        painelMotorDC.setLayout(painelMotorDCLayout);
        painelMotorDCLayout.setHorizontalGroup(
            painelMotorDCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelMotorDCLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(painelMotorDCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelMotorDCLayout.createSequentialGroup()
                        .addComponent(sliderForcaMotor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(painelMotorDCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelMotorDCLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(separadorForcaMotor, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(painelMotorDCLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(textFieldForcaMotor, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botaoMotorForca, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(painelMotorDCLayout.createSequentialGroup()
                        .addGroup(painelMotorDCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelMotorDCLayout.createSequentialGroup()
                                .addComponent(botaoMotorOn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(painelMotorDCLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(labelLigarMotor)
                                .addGap(28, 28, 28)))
                        .addGroup(painelMotorDCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botaoMotorOff, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(painelMotorDCLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(labelDesligarMotor)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(painelMotorDCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(painelMotorDCLayout.createSequentialGroup()
                                .addComponent(botaoMotorEsquerda, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(botaoMotorReverte, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(painelMotorDCLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(labelEsquerdaMotor)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelReverterMotor)))
                        .addGroup(painelMotorDCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelMotorDCLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botaoMotorDireita, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(painelMotorDCLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(labelDireitaMotor, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(painelMotorDCLayout.createSequentialGroup()
                        .addGap(281, 281, 281)
                        .addComponent(labelSetForcaMotor, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(17, 17, 17))
        );
        painelMotorDCLayout.setVerticalGroup(
            painelMotorDCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelMotorDCLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelMotorDCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(botaoMotorOff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botaoMotorDireita, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botaoMotorReverte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botaoMotorEsquerda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botaoMotorOn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelMotorDCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelLigarMotor)
                    .addComponent(labelDesligarMotor)
                    .addComponent(labelEsquerdaMotor)
                    .addComponent(labelReverterMotor)
                    .addComponent(labelDireitaMotor))
                .addGroup(painelMotorDCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelMotorDCLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(sliderForcaMotor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelMotorDCLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(painelMotorDCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(botaoMotorForca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(painelMotorDCLayout.createSequentialGroup()
                                .addComponent(textFieldForcaMotor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(separadorForcaMotor, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(8, 8, 8)
                        .addComponent(labelSetForcaMotor)))
                .addContainerGap())
        );

        painelTabMotor.addTab("      Motor DC      ", painelMotorDC);

        sliderPosicaoServo.setBorder(null);
        sliderPosicaoServo.setMaximum(40);
        sliderPosicaoServo.setMinimum(10);
        sliderPosicaoServo.setToolTipText("");
        sliderPosicaoServo.setFocusable(false);
        sliderPosicaoServo.setTrackShadeWidth(0);
        sliderPosicaoServo.setVerifyInputWhenFocusTarget(false);
        sliderPosicaoServo.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderPosicaoServoStateChanged(evt);
            }
        });

        textFieldPosicaoServo.setForeground(new java.awt.Color(255, 255, 255));
        textFieldPosicaoServo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textFieldPosicaoServo.setText("40");
        textFieldPosicaoServo.setToolTipText("");
        textFieldPosicaoServo.setDrawBackground(false);
        textFieldPosicaoServo.setDrawBorder(false);
        textFieldPosicaoServo.setFont(new java.awt.Font("Dialog", 0, 22)); // NOI18N
        textFieldPosicaoServo.setSelectionColor(new java.awt.Color(0, 153, 153));
        textFieldPosicaoServo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textFieldPosicaoServoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textFieldPosicaoServoKeyTyped(evt);
            }
        });

        botaoPosicaoServo.setBorder(null);
        botaoPosicaoServo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/portugol/plugin/gogoboard/imagens/monitor/ok.png"))); // NOI18N
        botaoPosicaoServo.setToolTipText("");
        botaoPosicaoServo.setContentAreaFilled(true);
        botaoPosicaoServo.setFocusable(false);
        botaoPosicaoServo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        botaoPosicaoServo.setOpaque(false);
        botaoPosicaoServo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoPosicaoServoActionPerformed(evt);
            }
        });

        labelSetPosicaoServo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelSetPosicaoServo.setText("Set Posição");

        javax.swing.GroupLayout painelMotorServoLayout = new javax.swing.GroupLayout(painelMotorServo);
        painelMotorServo.setLayout(painelMotorServoLayout);
        painelMotorServoLayout.setHorizontalGroup(
            painelMotorServoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelMotorServoLayout.createSequentialGroup()
                .addGroup(painelMotorServoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelMotorServoLayout.createSequentialGroup()
                        .addGroup(painelMotorServoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelMotorServoLayout.createSequentialGroup()
                                .addGap(57, 57, 57)
                                .addComponent(sliderPosicaoServo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(painelMotorServoLayout.createSequentialGroup()
                                .addGap(149, 149, 149)
                                .addComponent(botaoPosicaoServo, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(22, 22, 22)
                        .addGroup(painelMotorServoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(separadorPosicaoServo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textFieldPosicaoServo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(painelMotorServoLayout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addComponent(labelSetPosicaoServo, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(76, 76, 76))
        );
        painelMotorServoLayout.setVerticalGroup(
            painelMotorServoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelMotorServoLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(painelMotorServoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelMotorServoLayout.createSequentialGroup()
                        .addComponent(textFieldPosicaoServo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(separadorPosicaoServo, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(painelMotorServoLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(sliderPosicaoServo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(botaoPosicaoServo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelSetPosicaoServo)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        painelTabMotor.addTab("      Motor Servo      ", painelMotorServo);

        labelGoGo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelGoGo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/portugol/plugin/gogoboard/imagens/monitor/semGoGo.png"))); // NOI18N

        labelMotorA.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        labelMotorA.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelMotorA.setText("A  = 0");

        labelMotorB.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        labelMotorB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelMotorB.setText("B  = 0");

        labelMotorC.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        labelMotorC.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelMotorC.setText("C  = 0");

        labelMotorD.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        labelMotorD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelMotorD.setText("D  = 0");

        botaoMotorA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/portugol/plugin/gogoboard/imagens/monitor/esquerda.png"))); // NOI18N
        botaoMotorA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoMotorAActionPerformed(evt);
            }
        });

        botaoMotorB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/portugol/plugin/gogoboard/imagens/monitor/esquerda.png"))); // NOI18N
        botaoMotorB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoMotorBActionPerformed(evt);
            }
        });

        botaoMotorC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/portugol/plugin/gogoboard/imagens/monitor/esquerda.png"))); // NOI18N
        botaoMotorC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoMotorCActionPerformed(evt);
            }
        });

        botaoMotorD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/portugol/plugin/gogoboard/imagens/monitor/esquerda.png"))); // NOI18N
        botaoMotorD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoMotorDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelMotoresLayout = new javax.swing.GroupLayout(painelMotores);
        painelMotores.setLayout(painelMotoresLayout);
        painelMotoresLayout.setHorizontalGroup(
            painelMotoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelMotoresLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(painelMotoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelMotoresLayout.createSequentialGroup()
                        .addGroup(painelMotoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(botaoMotorA, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelMotorA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(painelMotoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(botaoMotorB, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelMotorB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(painelMotoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(botaoMotorC, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelMotorC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(painelMotoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(botaoMotorD, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelMotorD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(40, 40, 40))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelMotoresLayout.createSequentialGroup()
                        .addComponent(labelGoGo, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))))
        );
        painelMotoresLayout.setVerticalGroup(
            painelMotoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelMotoresLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(painelMotoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botaoMotorA, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelMotoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(botaoMotorB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(botaoMotorC, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(botaoMotorD, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelMotoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelMotorA)
                    .addComponent(labelMotorB)
                    .addComponent(labelMotorC)
                    .addComponent(labelMotorD))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelGoGo, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        botaoBeep.setBorder(null);
        botaoBeep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/portugol/plugin/gogoboard/imagens/monitor/beep.png"))); // NOI18N
        botaoBeep.setToolTipText("");
        botaoBeep.setContentAreaFilled(true);
        botaoBeep.setFocusable(false);
        botaoBeep.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        botaoBeep.setOpaque(false);
        botaoBeep.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/portugol/plugin/gogoboard/imagens/monitor/beep_pres.png"))); // NOI18N
        botaoBeep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoBeepActionPerformed(evt);
            }
        });

        labelIconeIR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/portugol/plugin/gogoboard/imagens/monitor/controle_remoto.png"))); // NOI18N
        labelIconeIR.setToolTipText("");

        textFieldSetDisplay.setDocument(new JTextFieldLimit(4));
        textFieldSetDisplay.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textFieldSetDisplay.setText("GoGo");
        textFieldSetDisplay.setToolTipText("");
        textFieldSetDisplay.setDrawBackground(false);
        textFieldSetDisplay.setDrawFocus(false);
        textFieldSetDisplay.setDrawShade(false);
        textFieldSetDisplay.setFont(new java.awt.Font("Dialog", 0, 22)); // NOI18N
        textFieldSetDisplay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textFieldSetDisplayKeyTyped(evt);
            }
        });

        botaoSetDisplay.setBorder(null);
        botaoSetDisplay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/portugol/plugin/gogoboard/imagens/monitor/ok.png"))); // NOI18N
        botaoSetDisplay.setToolTipText("");
        botaoSetDisplay.setContentAreaFilled(true);
        botaoSetDisplay.setFocusable(false);
        botaoSetDisplay.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        botaoSetDisplay.setOpaque(false);
        botaoSetDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSetDisplayActionPerformed(evt);
            }
        });

        labelSetDisplay.setText("Exibir");

        labelTituloDisplay.setFont(new java.awt.Font("Dialog", 0, 22)); // NOI18N
        labelTituloDisplay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTituloDisplay.setText("Display 7 seguimentos");
        labelTituloDisplay.setOpaque(true);

        labelBeep.setText("Beep");

        labelLed.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelLed.setText("Ligar Led");

        labelIR.setText("Código  = 0");

        botaoLedOn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/univali/portugol/plugin/gogoboard/imagens/monitor/led_on.png"))); // NOI18N
        botaoLedOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoLedOnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelOutrasAcoesLayout = new javax.swing.GroupLayout(painelOutrasAcoes);
        painelOutrasAcoes.setLayout(painelOutrasAcoesLayout);
        painelOutrasAcoesLayout.setHorizontalGroup(
            painelOutrasAcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelOutrasAcoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelOutrasAcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelOutrasAcoesLayout.createSequentialGroup()
                        .addGroup(painelOutrasAcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botaoBeep, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(painelOutrasAcoesLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(labelBeep)))
                        .addGroup(painelOutrasAcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelOutrasAcoesLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelLed, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(painelOutrasAcoesLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(botaoLedOn, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(painelOutrasAcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelIR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(painelOutrasAcoesLayout.createSequentialGroup()
                                .addComponent(labelIconeIR)
                                .addGap(0, 23, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelOutrasAcoesLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(painelOutrasAcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(textFieldSetDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(separadorInputDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(painelOutrasAcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelOutrasAcoesLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(botaoSetDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(painelOutrasAcoesLayout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(labelSetDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(labelTituloDisplay, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        painelOutrasAcoesLayout.setVerticalGroup(
            painelOutrasAcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelOutrasAcoesLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(painelOutrasAcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painelOutrasAcoesLayout.createSequentialGroup()
                        .addComponent(labelIconeIR)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(botaoBeep, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botaoLedOn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelOutrasAcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelBeep)
                    .addComponent(labelLed)
                    .addComponent(labelIR))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelTituloDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(painelOutrasAcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelOutrasAcoesLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textFieldSetDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(separadorInputDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelOutrasAcoesLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(botaoSetDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelSetDisplay)))
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout painelPrincipalLayout = new javax.swing.GroupLayout(painelPrincipal);
        painelPrincipal.setLayout(painelPrincipalLayout);
        painelPrincipalLayout.setHorizontalGroup(
            painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPrincipalLayout.createSequentialGroup()
                .addGroup(painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelPrincipalLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(painelSensor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(painelPrincipalLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(painelTabMotor, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(painelMotores, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(painelOutrasAcoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(515, 515, 515))
        );
        painelPrincipalLayout.setVerticalGroup(
            painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPrincipalLayout.createSequentialGroup()
                .addGroup(painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelPrincipalLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(painelTabMotor, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(painelMotores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(painelPrincipalLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(painelOutrasAcoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(painelSensor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 1073, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void botaoMotorOnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoMotorOnActionPerformed
        if (dispositivoGoGo.isConectado()) {
            try {
                if (botaoMotorA.isSelected()) {
                    dispositivoGoGo.getMotoresDC().get('a').ligar();
                    labelMotorA.setText("A = " + sliderForcaMotor.getValue());
                }
                if (botaoMotorB.isSelected()) {
                    dispositivoGoGo.getMotoresDC().get('b').ligar();
                    labelMotorB.setText("B = " + sliderForcaMotor.getValue());
                }
                if (botaoMotorC.isSelected()) {
                    dispositivoGoGo.getMotoresDC().get('c').ligar();
                    labelMotorC.setText("C = " + sliderForcaMotor.getValue());
                }
                if (botaoMotorD.isSelected()) {
                    dispositivoGoGo.getMotoresDC().get('d').ligar();
                    labelMotorD.setText("D = " + sliderForcaMotor.getValue());
                }
                atualizarStatusBotoesMotoresDC();
            } catch (ErroExecucaoBiblioteca ex) {
                Logger.getLogger(JanelaMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_botaoMotorOnActionPerformed

    private void sliderForcaMotorStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderForcaMotorStateChanged
        textFieldForcaMotor.setText(Integer.toString(sliderForcaMotor.getValue()));
    }//GEN-LAST:event_sliderForcaMotorStateChanged

    private void textFieldForcaMotorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldForcaMotorKeyReleased
        try {
            int forca = Integer.parseInt(textFieldForcaMotor.getText());
            if (forca <= 100) {
                sliderForcaMotor.setValue(forca);
            } else {
                sliderForcaMotor.setValue(100);
                textFieldForcaMotor.setText(String.valueOf(100));
            }
        } catch (NumberFormatException ex) {
            sliderForcaMotor.setValue(0);
            textFieldForcaMotor.setText(String.valueOf(0));
        }
    }//GEN-LAST:event_textFieldForcaMotorKeyReleased

    private void textFieldForcaMotorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldForcaMotorKeyTyped
        String caracteresAceitos = "0987654321";
        if (!caracteresAceitos.contains(String.valueOf(evt.getKeyChar()))) {
            evt.consume();
        }
    }//GEN-LAST:event_textFieldForcaMotorKeyTyped

    private void botaoMotorDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoMotorDActionPerformed
        if (dispositivoGoGo.isConectado()) {
            if (painelTabMotor.getSelectedIndex() == 0) {
                definirIconeMotores(dispositivoGoGo.getMotorDC('d'), botaoMotorD);
            } else {
                definirIconeMotores(dispositivoGoGo.getMotorServo('d'), botaoMotorD);
            }
        }
    }//GEN-LAST:event_botaoMotorDActionPerformed

    private void botaoMotorBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoMotorBActionPerformed
        if (dispositivoGoGo.isConectado()) {
            if (painelTabMotor.getSelectedIndex() == 0) {
                definirIconeMotores(dispositivoGoGo.getMotorDC('b'), botaoMotorB);
            } else {
                definirIconeMotores(dispositivoGoGo.getMotorServo('b'), botaoMotorB);
            }
        }
    }//GEN-LAST:event_botaoMotorBActionPerformed

    private void botaoMotorCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoMotorCActionPerformed
        if (dispositivoGoGo.isConectado()) {
            if (painelTabMotor.getSelectedIndex() == 0) {
                definirIconeMotores(dispositivoGoGo.getMotorDC('c'), botaoMotorC);
            } else {
                definirIconeMotores(dispositivoGoGo.getMotorServo('c'), botaoMotorC);
            }
        }
    }//GEN-LAST:event_botaoMotorCActionPerformed

    private void botaoMotorAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoMotorAActionPerformed
        if (dispositivoGoGo.isConectado()) {
            if (painelTabMotor.getSelectedIndex() == 0) {
                definirIconeMotores(dispositivoGoGo.getMotorDC('a'), botaoMotorA);
            } else {
                definirIconeMotores(dispositivoGoGo.getMotorServo('a'), botaoMotorA);
            }
        }
    }//GEN-LAST:event_botaoMotorAActionPerformed

    private void textFieldSetDisplayKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldSetDisplayKeyTyped
        if (textFieldSetDisplay.getText().length() > 4) {
            evt.consume();
        }
    }//GEN-LAST:event_textFieldSetDisplayKeyTyped

    private void botaoLedOnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoLedOnActionPerformed
        if (dispositivoGoGo.isConectado()) {
            try {
                if (botaoLedOn.isSelected()) {
                    botaoLedOn.setIcon(iconeLed_off);
                    labelLed.setText("Desigar Led");
                    dispositivoGoGo.getLedUsuario().controlarLed(1);
                } else {
                    botaoLedOn.setIcon(iconeLed_on);
                    labelLed.setText("Ligar Led");
                    dispositivoGoGo.getLedUsuario().controlarLed(0);
                }
            } catch (ErroExecucaoBiblioteca ex) {
                Logger.getLogger(JanelaMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_botaoLedOnActionPerformed

    private void botaoBeepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoBeepActionPerformed
        if (dispositivoGoGo.isConectado()) {
            try {
                dispositivoGoGo.getBuzzer().acionar();
            } catch (ErroExecucaoBiblioteca ex) {
                Logger.getLogger(JanelaMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_botaoBeepActionPerformed

    private void botaoSetDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSetDisplayActionPerformed
        if (dispositivoGoGo.isConectado()) {
            try {
                dispositivoGoGo.getDisplay().exibirPalavra(textFieldSetDisplay.getText());
            } catch (ErroExecucaoBiblioteca ex) {
                Logger.getLogger(JanelaMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_botaoSetDisplayActionPerformed

    private void botaoMotorOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoMotorOffActionPerformed
        if (dispositivoGoGo.isConectado()) {
            try {
                if (botaoMotorA.isSelected()) {
                    dispositivoGoGo.getMotoresDC().get('a').desligar();
                    labelMotorA.setText("A = 0");
                }
                if (botaoMotorB.isSelected()) {
                    dispositivoGoGo.getMotoresDC().get('b').desligar();
                    labelMotorB.setText("B = 0");
                }
                if (botaoMotorC.isSelected()) {
                    dispositivoGoGo.getMotoresDC().get('c').desligar();
                    labelMotorC.setText("C = 0");
                }
                if (botaoMotorD.isSelected()) {
                    dispositivoGoGo.getMotoresDC().get('d').desligar();
                    labelMotorD.setText("D = 0");
                }
                atualizarStatusBotoesMotoresDC();
            } catch (ErroExecucaoBiblioteca ex) {
                Logger.getLogger(JanelaMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_botaoMotorOffActionPerformed

    private void botaoMotorEsquerdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoMotorEsquerdaActionPerformed
        if (dispositivoGoGo.isConectado()) {
            definirDirecaoMotor(0);
        }
    }//GEN-LAST:event_botaoMotorEsquerdaActionPerformed

    private void botaoMotorDireitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoMotorDireitaActionPerformed
        if (dispositivoGoGo.isConectado()) {
            definirDirecaoMotor(1);
        }
    }//GEN-LAST:event_botaoMotorDireitaActionPerformed

    private void definirDirecaoMotor(int direcao) {
        if (dispositivoGoGo.isConectado()) {
            try {
                if (botaoMotorA.isSelected() && dispositivoGoGo.getMotoresDC().get('a').isLigado()) {
                    dispositivoGoGo.getMotoresDC().get('a').definirDirecao(direcao);
                }
                if (botaoMotorB.isSelected() && dispositivoGoGo.getMotoresDC().get('b').isLigado()) {
                    dispositivoGoGo.getMotoresDC().get('b').definirDirecao(direcao);
                }
                if (botaoMotorC.isSelected() && dispositivoGoGo.getMotoresDC().get('c').isLigado()) {
                    dispositivoGoGo.getMotoresDC().get('c').definirDirecao(direcao);
                }
                if (botaoMotorD.isSelected() && dispositivoGoGo.getMotoresDC().get('d').isLigado()) {
                    dispositivoGoGo.getMotoresDC().get('d').definirDirecao(direcao);
                }
                atualizarStatusBotoesMotoresDC();
            } catch (ErroExecucaoBiblioteca ex) {
                Logger.getLogger(JanelaMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void botaoMotorReverteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoMotorReverteActionPerformed
        if (dispositivoGoGo.isConectado()) {
            try {
                if (botaoMotorA.isSelected() && dispositivoGoGo.getMotoresDC().get('a').isLigado()) {
                    dispositivoGoGo.getMotoresDC().get('a').inverterDirecao();
                }
                if (botaoMotorB.isSelected() && dispositivoGoGo.getMotoresDC().get('b').isLigado()) {
                    dispositivoGoGo.getMotoresDC().get('b').inverterDirecao();
                }
                if (botaoMotorC.isSelected() && dispositivoGoGo.getMotoresDC().get('c').isLigado()) {
                    dispositivoGoGo.getMotoresDC().get('c').inverterDirecao();
                }
                if (botaoMotorD.isSelected() && dispositivoGoGo.getMotoresDC().get('d').isLigado()) {
                    dispositivoGoGo.getMotoresDC().get('d').inverterDirecao();
                }
                atualizarStatusBotoesMotoresDC();
            } catch (ErroExecucaoBiblioteca ex) {
                Logger.getLogger(JanelaMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_botaoMotorReverteActionPerformed

    private void botaoMotorForcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoMotorForcaActionPerformed
        if (dispositivoGoGo.isConectado()) {
            try {
                if (botaoMotorA.isSelected() && dispositivoGoGo.getMotoresDC().get('a').isLigado()) {
                    dispositivoGoGo.getMotoresDC().get('a').definirForca(sliderForcaMotor.getValue());
                    labelMotorA.setText("A = " + sliderForcaMotor.getValue());
                }
                if (botaoMotorB.isSelected() && dispositivoGoGo.getMotoresDC().get('b').isLigado()) {
                    dispositivoGoGo.getMotoresDC().get('b').definirForca(sliderForcaMotor.getValue());
                    labelMotorB.setText("B = " + sliderForcaMotor.getValue());
                }
                if (botaoMotorC.isSelected() && dispositivoGoGo.getMotoresDC().get('c').isLigado()) {
                    dispositivoGoGo.getMotoresDC().get('c').definirForca(sliderForcaMotor.getValue());
                    labelMotorC.setText("C = " + sliderForcaMotor.getValue());
                }
                if (botaoMotorD.isSelected() && dispositivoGoGo.getMotoresDC().get('d').isLigado()) {
                    dispositivoGoGo.getMotoresDC().get('d').definirForca(sliderForcaMotor.getValue());
                    labelMotorD.setText("D = " + sliderForcaMotor.getValue());
                }
                atualizarStatusBotoesMotoresDC();
            } catch (ErroExecucaoBiblioteca ex) {
                Logger.getLogger(JanelaMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_botaoMotorForcaActionPerformed

    private void definirPosicaoServo() {
        MotorServo servo;
        if (dispositivoGoGo.isConectado()) {
            try {
                if (botaoMotorA.isSelected()) {
                    servo = dispositivoGoGo.getMotoresServo().get('a');
                    servo.setPosicao(sliderPosicaoServo.getValue());
                    servo.setLigado(true);
                    servo.setDireita(true);
                    labelMotorA.setText("A = " + sliderPosicaoServo.getValue());
                }
                if (botaoMotorB.isSelected()) {
                    servo = dispositivoGoGo.getMotoresServo().get('b');
                    servo.setPosicao(sliderPosicaoServo.getValue());
                    servo.setLigado(true);
                    servo.setDireita(true);
                    labelMotorB.setText("B = " + sliderPosicaoServo.getValue());
                }
                if (botaoMotorC.isSelected()) {
                    servo = dispositivoGoGo.getMotoresServo().get('c');
                    servo.setPosicao(sliderPosicaoServo.getValue());
                    servo.setLigado(true);
                    servo.setDireita(true);
                    labelMotorC.setText("C = " + sliderPosicaoServo.getValue());
                }
                if (botaoMotorD.isSelected()) {
                    servo = dispositivoGoGo.getMotoresServo().get('d');
                    servo.setPosicao(sliderPosicaoServo.getValue());
                    servo.setLigado(true);
                    servo.setDireita(true);
                    labelMotorD.setText("D = " + sliderPosicaoServo.getValue());
                }
                atualizarStatusBotoesMotoresServo();
            } catch (ErroExecucaoBiblioteca ex) {
                Logger.getLogger(JanelaMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void sliderPosicaoServoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderPosicaoServoStateChanged
        textFieldPosicaoServo.setText(Integer.toString(sliderPosicaoServo.getValue()));
        definirPosicaoServo();
    }//GEN-LAST:event_sliderPosicaoServoStateChanged

    private void textFieldPosicaoServoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldPosicaoServoKeyTyped
        String caracteresAceitos = "0987654321";
        if (!caracteresAceitos.contains(String.valueOf(evt.getKeyChar()))) {
            evt.consume();
        }
    }//GEN-LAST:event_textFieldPosicaoServoKeyTyped


    private void botaoPosicaoServoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoPosicaoServoActionPerformed
        definirPosicaoServo();
    }//GEN-LAST:event_botaoPosicaoServoActionPerformed

    private void textFieldPosicaoServoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldPosicaoServoKeyReleased
        try {
            int forca = Integer.parseInt(textFieldPosicaoServo.getText());
            if (forca <= 40 && forca >= 10) {
                sliderPosicaoServo.setValue(forca);
            } else if (forca > 40) {
                sliderPosicaoServo.setValue(40);
                textFieldPosicaoServo.setText(String.valueOf(40));
            }
        } catch (NumberFormatException ex) {
        }
    }//GEN-LAST:event_textFieldPosicaoServoKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.laf.button.WebButton botaoBeep;
    private com.alee.laf.button.WebToggleButton botaoLedOn;
    private com.alee.laf.button.WebToggleButton botaoMotorA;
    private com.alee.laf.button.WebToggleButton botaoMotorB;
    private com.alee.laf.button.WebToggleButton botaoMotorC;
    private com.alee.laf.button.WebToggleButton botaoMotorD;
    private com.alee.laf.button.WebButton botaoMotorDireita;
    private com.alee.laf.button.WebButton botaoMotorEsquerda;
    private com.alee.laf.button.WebButton botaoMotorForca;
    private com.alee.laf.button.WebButton botaoMotorOff;
    private com.alee.laf.button.WebButton botaoMotorOn;
    private com.alee.laf.button.WebButton botaoMotorReverte;
    private com.alee.laf.button.WebButton botaoPosicaoServo;
    private com.alee.laf.button.WebButton botaoSetDisplay;
    private javax.swing.JLabel labelBeep;
    private javax.swing.JLabel labelDesligarMotor;
    private javax.swing.JLabel labelDireitaMotor;
    private javax.swing.JLabel labelEsquerdaMotor;
    private javax.swing.JLabel labelGoGo;
    private javax.swing.JLabel labelIR;
    private javax.swing.JLabel labelIconeIR;
    private javax.swing.JLabel labelLed;
    private javax.swing.JLabel labelLigarMotor;
    private javax.swing.JLabel labelMotorA;
    private javax.swing.JLabel labelMotorB;
    private javax.swing.JLabel labelMotorC;
    private javax.swing.JLabel labelMotorD;
    private javax.swing.JLabel labelReverterMotor;
    private javax.swing.JLabel labelSensor1;
    private javax.swing.JLabel labelSensor2;
    private javax.swing.JLabel labelSensor3;
    private javax.swing.JLabel labelSensor4;
    private javax.swing.JLabel labelSensor5;
    private javax.swing.JLabel labelSensor6;
    private javax.swing.JLabel labelSensor7;
    private javax.swing.JLabel labelSensor8;
    private javax.swing.JLabel labelSetDisplay;
    private javax.swing.JLabel labelSetForcaMotor;
    private javax.swing.JLabel labelSetPosicaoServo;
    private javax.swing.JLabel labelTituloDisplay;
    private javax.swing.JPanel painelMotorDC;
    private javax.swing.JPanel painelMotorServo;
    private javax.swing.JPanel painelMotores;
    private javax.swing.JPanel painelOutrasAcoes;
    private javax.swing.JPanel painelPrincipal;
    private javax.swing.JPanel painelSensor;
    private com.alee.laf.tabbedpane.WebTabbedPane painelTabMotor;
    private javax.swing.JProgressBar progressBarSensor1;
    private javax.swing.JProgressBar progressBarSensor2;
    private javax.swing.JProgressBar progressBarSensor3;
    private javax.swing.JProgressBar progressBarSensor4;
    private javax.swing.JProgressBar progressBarSensor5;
    private javax.swing.JProgressBar progressBarSensor6;
    private javax.swing.JProgressBar progressBarSensor7;
    private javax.swing.JProgressBar progressBarSensor8;
    private com.alee.laf.separator.WebSeparator separadorForcaMotor;
    private com.alee.laf.separator.WebSeparator separadorInputDisplay;
    private com.alee.laf.separator.WebSeparator separadorPosicaoServo;
    private com.alee.laf.slider.WebSlider sliderForcaMotor;
    private com.alee.laf.slider.WebSlider sliderPosicaoServo;
    private com.alee.laf.text.WebTextField textFieldForcaMotor;
    private com.alee.laf.text.WebTextField textFieldPosicaoServo;
    private com.alee.laf.text.WebTextField textFieldSetDisplay;
    // End of variables declaration//GEN-END:variables
}
