package principal;

import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;

public class interfaz extends javax.swing.JFrame {

    NumeroLinea nl;
    String lexicoAlfabeto = "0123456789+-/*=(),;. \nABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz$", cadenaEntrada, temporalId, estadoAnterior, temporalValor, estadoSintacticoActual, valorExprecion;
    String palabrasReservadas[] = {"int","float","char"}, terminalesNoterminaleSintactico[] = {"id","num","int","float","char",",",";","+","-","*","/","(",")","=","$","P","Tipo","V","A","Exp","E","Term","T","F"};
    String noterminalProducciones [] = {"p'", "P", "P", "Tipo", "Tipo", "Tipo", "V", "V", "A", "Exp", "Exp", "Exp", "E", "E", "E", "Term", "T", "T", "T", "F", "F", "F"};
    String estadosSemanticos[] = {"I18", "I19", "I20", "I27", "I28", "I30", "I31", "I40"}, codigoIntermedioSalida = "";
    Vector<String> tokenEsperados = new Vector<>(1,1);
    HashMap<String, Integer> tablaSimbolos = new HashMap<>();
    Stack<String> pilaSintactica = new Stack<>();
    Stack<Integer> pilaSemantica = new Stack<>();
    Stack<Integer> pilaOperadores = new Stack<>();
    boolean banProduccion = false, error = false;
    int estado = 0, saltoLinea = 1, estadoSintactico = 0, tokenEntrada = 0, tipoDato = 0, datoExprecion;
    int producciones[] = {0, 6, 2, 2, 2, 2, 6, 4, 8, 6, 6, 4, 6, 6, 0, 4, 6, 6, 0, 2, 2, 6};
    int tablaLexico[][] = {
        { 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 6, 6, 6, 6, 6, 6, 6, 6,-1, 0, 0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 7},
        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 6, 6, 6,-1, 6, 6,-1, 6, 2, 0, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 7},
        { 2, 3, 3, 3, 3, 3, 3, 3, 3, 3,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 7},
        { 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 6, 6, 6, 6,-1, 6, 6,-1, 6,-1, 0, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 7},
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 6, 6, 6, 6,-1, 6, 6,-1, 6, 2, 0, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 7},
        { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6,-1, 0, 0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 7},
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 0, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 7}
    };
    String tablaSintactica[][] = {
        { "I7", "-1", "I4", "I5", "I6", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "I1", "I2", "-1", "I3", "-1", "-1", "-1", "-1", "-1"},
        { "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "P0", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"},
        { "I8", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"},
        { "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "P2", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"},
        { "P3", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"},
        { "P4", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"},
        { "P5", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"},
        { "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "I9", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"},
        { "-1", "-1", "-1", "-1", "-1","I11","I12", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1","I10", "-1", "-1", "-1", "-1", "-1", "-1"},
        {"I18","I19", "-1", "-1", "-1", "-1", "-1","I14","I15", "-1", "-1","I20", "-1", "-1", "-1", "-1", "-1", "-1", "-1","I13", "-1","I16", "-1","I17"},
        { "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "P1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"},
        {"I21", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"},
        { "I7", "-1", "I4", "I5", "I6", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1","I22", "I2", "-1", "I3", "-1", "-1", "-1", "-1", "-1"},
        { "-1", "-1", "-1", "-1", "-1", "-1","I23", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"},
        {"I18","I19", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1","I20", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1","I24", "-1","I17"},
        {"I18","I19", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1","I20", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1","I25", "-1","I17"},
        { "-1", "-1", "-1", "-1", "-1", "-1","P14","I27","I28", "-1", "-1", "-1","P14", "-1", "-1", "-1", "-1", "-1", "-1", "-1","I26", "-1", "-1", "-1"},
        { "-1", "-1", "-1", "-1", "-1", "-1","P18","P18","P18","I30","I31", "-1","P18", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1","I29", "-1"},
        { "-1", "-1", "-1", "-1", "-1", "-1","P19","P19","P19","P19","P19", "-1","P19", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"},
        { "-1", "-1", "-1", "-1", "-1", "-1","P20","P20","P20","P20","P20", "-1","P20", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"},
        {"I18","I19", "-1", "-1", "-1", "-1", "-1","I14","I15", "-1", "-1","I20", "-1", "-1", "-1", "-1", "-1", "-1", "-1","I32", "-1","I16", "-1","I17"},
        { "-1", "-1", "-1", "-1", "-1","I11","I12", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1","I33", "-1", "-1", "-1", "-1", "-1", "-1"},
        { "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "P7", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"},
        { "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "P8", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"},
        { "-1", "-1", "-1", "-1", "-1", "-1","P14","I27","I28", "-1", "-1", "-1","P14", "-1", "-1", "-1", "-1", "-1", "-1", "-1","I34", "-1", "-1", "-1"},
        { "-1", "-1", "-1", "-1", "-1", "-1","P14","I27","I28", "-1", "-1", "-1","P14", "-1", "-1", "-1", "-1", "-1", "-1", "-1","I35", "-1", "-1", "-1"},
        { "-1", "-1", "-1", "-1", "-1", "-1","P11", "-1", "-1", "-1", "-1", "-1","P11", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"},
        {"I18","I19", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1","I20", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1","I36", "-1","I17"},
        {"I18","I19", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1","I20", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1","I37", "-1","I17"},
        { "-1", "-1", "-1", "-1", "-1", "-1","P15","P15","P15", "-1", "-1", "-1","P15", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"},
        {"I18","I19", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1","I20", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1","I38"},
        {"I18","I19", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1","I20", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1","I39"},
        { "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1","I40", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"},
        { "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "P6", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"},
        { "-1", "-1", "-1", "-1", "-1", "-1", "P9", "-1", "-1", "-1", "-1", "-1", "P9", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"},
        { "-1", "-1", "-1", "-1", "-1", "-1","P10", "-1", "-1", "-1", "-1", "-1","P10", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"},
        { "-1", "-1", "-1", "-1", "-1", "-1","P14","I27","I28", "-1", "-1", "-1","P14", "-1", "-1", "-1", "-1", "-1", "-1", "-1","I41", "-1", "-1", "-1"},
        { "-1", "-1", "-1", "-1", "-1", "-1","P14","I27","I28", "-1", "-1", "-1","P14", "-1", "-1", "-1", "-1", "-1", "-1", "-1","I42", "-1", "-1", "-1"},
        { "-1", "-1", "-1", "-1", "-1", "-1","P18","P18","P18","I30","I31", "-1","P18", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1","I43", "-1"},
        { "-1", "-1", "-1", "-1", "-1", "-1","P18","P18","P18","I30","I31", "-1","P18", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1","I44", "-1"},
        { "-1", "-1", "-1", "-1", "-1", "-1","P21","P21","P21","P21","P21", "-1","P21", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"},
        { "-1", "-1", "-1", "-1", "-1", "-1","P12", "-1", "-1", "-1", "-1", "-1","P12", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"},
        { "-1", "-1", "-1", "-1", "-1", "-1","P13", "-1", "-1", "-1", "-1", "-1","P13", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"},
        { "-1", "-1", "-1", "-1", "-1", "-1","P16","P16","P16", "-1", "-1", "-1","P16", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"},
        { "-1", "-1", "-1", "-1", "-1", "-1","P17","P17","P17", "-1", "-1", "-1","P17", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"},
    };
    
    int tablaSemantica[][] = {
        { 0, 1,-1},
        { 1, 1,-1},
        {-1,-1,-1}
    };
    
    public interfaz() {
        initComponents();
        nl = new NumeroLinea(inTexto);
        inRaiz.setRowHeaderView(nl);
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        inRaiz = new javax.swing.JScrollPane();
        inTexto = new javax.swing.JTextArea();
        correr = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        vistaAnalizadorSintactico = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        vistaAnalizadorLexico = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        vistaError = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        AnalisisSemantico = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        inTexto.setColumns(20);
        inTexto.setRows(5);
        inRaiz.setViewportView(inTexto);

        correr.setText("Correr");
        correr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                correrActionPerformed(evt);
            }
        });

        vistaAnalizadorSintactico.setColumns(20);
        vistaAnalizadorSintactico.setRows(5);
        jScrollPane2.setViewportView(vistaAnalizadorSintactico);

        vistaAnalizadorLexico.setColumns(20);
        vistaAnalizadorLexico.setRows(5);
        jScrollPane1.setViewportView(vistaAnalizadorLexico);

        vistaError.setColumns(20);
        vistaError.setRows(5);
        jScrollPane3.setViewportView(vistaError);

        AnalisisSemantico.setColumns(20);
        AnalisisSemantico.setRows(5);
        jScrollPane4.setViewportView(AnalisisSemantico);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(inRaiz, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 250, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(23, 23, 23))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(71, 71, 71)
                                .addComponent(correr)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(inRaiz, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                        .addComponent(correr)
                        .addGap(3, 3, 3)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(132, 132, 132))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void correrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_correrActionPerformed
        this.IniciolizarVariables();
        this.AnalizadorLexico();
    }//GEN-LAST:event_correrActionPerformed
    
    public void IniciolizarVariables()
    {
        error = false;
        temporalId = "";
        estadoAnterior = "";
        codigoIntermedioSalida = "";
        cadenaEntrada = inTexto.getText();
        cadenaEntrada += "$";
        pilaSintactica.clear();
        pilaOperadores.clear();
        pilaSemantica.clear();
        pilaSintactica.push("$");
        pilaSintactica.push("I0");
        estado = 0;
        saltoLinea = 1;
        estadoSintactico = 0;
        tokenEntrada = 0;
        vistaAnalizadorLexico.setText("");
        vistaAnalizadorSintactico.setText("");
        vistaError.setText("");
        tokenEsperados.removeAllElements();
        valorExprecion = "";
        datoExprecion = 0;
        tipoDato = 0;
        temporalValor = "";
        tablaSimbolos.clear();
    }
    
    public void AnalizadorLexico()
    {
        int i, posicion;
        boolean banCadena = true;
        
        for(i = 0; i < cadenaEntrada.length(); i++)
        {
            posicion = lexicoAlfabeto.indexOf(cadenaEntrada.charAt(i));
            
            if(posicion == -1)
            {
                this.Error("Lexico", saltoLinea, "");
                break;
            }
            else
                estado = tablaLexico[estado][posicion];
            
            if(estado == -1)
            {
                this.Error("Lexico", saltoLinea, "");
                break;
            }
            
            if(estado == 5)
                if(banCadena)
                {
                    temporalId = cadenaEntrada.charAt(i) + "";
                    banCadena = false;
                }
                else
                    temporalId += cadenaEntrada.charAt(i);
            if(i + 1 < cadenaEntrada.length() && lexicoAlfabeto.indexOf(cadenaEntrada.charAt(i + 1)) != -1 )
            {
                if(tablaLexico[estado][lexicoAlfabeto.indexOf(cadenaEntrada.charAt(i + 1))] == 0 || tablaLexico[estado][lexicoAlfabeto.indexOf(cadenaEntrada.charAt(i +1))] == 6)
                    this.ComprobarEstados(i);
                else
                    if(estado == 6 || estado == 7)
                        this.ComprobarEstados(i);
            }
            else
                if(estado == 6 || estado == 7)
                        this.ComprobarEstados(i);        
                      
            if(error)
                return;
            
            if(cadenaEntrada.charAt(i) == '\n')
                saltoLinea++;
        }
    }
    
    public void ComprobarEstados(int i)
    {
        switch(estado)
        {
            case 1 ->
            {
                this.EnviarToken("num");
                tipoDato = 0;
            }
            case 3 ->
            {
                this.EnviarToken("num");
                tipoDato = 1;
            }
            case 4 ->
            {
                this.EnviarToken("num");
                tipoDato = 0;
            }
            case 5 -> this.ComprobarId(i);
            case 6 -> this.EnviarToken(cadenaEntrada.charAt(i) + "");
            case 7 -> this.TerminadorCadena(i);
        }
        
    }
    
    public void ComprobarId(int i)
    {
        boolean banId = false;
        
        for(String reservadas : palabrasReservadas)
            if(temporalId.compareTo(reservadas) == 0)
                banId = true;
        if(banId)
        {
            switch(temporalId)
            {
                case "int" -> tipoDato = 0;
                case "float" -> tipoDato = 1;
                case "char" -> tipoDato = 2;
            }
            this.EnviarToken(temporalId);
        }
        else
        {
            temporalValor = temporalId;
            this.EnviarToken("id");
        }
        temporalId = "";
    }
    
    public void TerminadorCadena(int i)
    {
        if(i == cadenaEntrada.length() - 1)
            this.EnviarToken("$");
        else
            this.Error("Lexico", saltoLinea, "");
    }
    
    public void EnviarToken(String token)
    {
        vistaAnalizadorLexico.append(token + "\n");
        this.AnalizadorSintactico(token);
    }
    
    public void AnalizadorSintactico(String token)
    {
        do
        {
            switch(pilaSintactica.peek().charAt(0))
            {
                case 'I' -> this.EstadosSintacticos(token);
                case 'P' -> this.Producciones(token);
                default -> this.Error("Sintactico", saltoLinea, token);
            }
            if(error)
                break;
        }while(banProduccion);        
    }
    
    @SuppressWarnings("empty-statement")
    public void EstadosSintacticos(String token)
    {
        estadoAnterior = pilaSintactica.peek().substring(1);
        estadoSintactico = Integer.parseInt(estadoAnterior);
        for(tokenEntrada = 0; tokenEntrada < terminalesNoterminaleSintactico.length && !terminalesNoterminaleSintactico[tokenEntrada].equals(token); tokenEntrada++);
        
        switch(tablaSintactica[estadoSintactico][tokenEntrada].charAt(0))
        {
            case 'I' ->
            {
                pilaSintactica.push(token);
                pilaSintactica.push(tablaSintactica[estadoSintactico][tokenEntrada]);
                vistaAnalizadorSintactico.append(String.valueOf(pilaSintactica) + "\n");
                banProduccion = false;
                if(pilaSintactica.peek().equals("I8") || pilaSintactica.peek().equals("I21"))
                    this.TablaSimbolos();
                if(pilaSintactica.peek().equals("I7"))
                    this.ValidarIdSemantico();
                if(pilaSintactica.peek().equals("I23"))
                    this.AnalisisExprecion();
                for(String i : estadosSemanticos)
                    if(pilaSintactica.peek().equals(i))
                        AnalizadorSemantico(token);
            }
            case 'P' -> this.Producciones(tablaSintactica[estadoSintactico][tokenEntrada]);
            default -> this.Error("Sintactico", saltoLinea, token);
        }
    }
    
    @SuppressWarnings("empty-statement")
    public void Producciones(String produccion)
    {
        if(!produccion.equals("P0"))
        {
            int nProduccion, nPops, x;
            nProduccion = Integer.parseInt(produccion.substring(1));
            nPops = producciones[nProduccion];

            for(x = 0; x < nPops; x++)
                pilaSintactica.pop();

            estadoSintactico = Integer.parseInt(pilaSintactica.peek().substring(1));
            for(tokenEntrada = 0; tokenEntrada < terminalesNoterminaleSintactico.length && !terminalesNoterminaleSintactico[tokenEntrada].equals(noterminalProducciones[nProduccion]); tokenEntrada++);

            pilaSintactica.push(noterminalProducciones[nProduccion]);
            pilaSintactica.push(tablaSintactica[estadoSintactico][tokenEntrada]);
            vistaAnalizadorSintactico.append(String.valueOf(pilaSintactica) + "\n");
            banProduccion = true;
        }
        else
        {
            vistaAnalizadorSintactico.append("Se Acepta");
            banProduccion = false;
        }
    }
    
    public void TablaSimbolos()
    {
        boolean variableRepetida = true;
        if(!tablaSimbolos.isEmpty())
            for(String var : tablaSimbolos.keySet())
                if(temporalValor.equals(var))
                    variableRepetida = false;
        if(variableRepetida)
        {
            tablaSimbolos.put(temporalValor, tipoDato);
            switch(tablaSimbolos.get(temporalValor))
            {
                case 0 -> codigoIntermedioSalida += "int " + temporalValor + ";\n";
                case 1 -> codigoIntermedioSalida += "float " + temporalValor + ";\n";
                case 2 -> codigoIntermedioSalida += "char " + temporalValor + ";\n";
            }
        }
        else
            this.Error("Semantico", saltoLinea, "No se pueden declarar dos id iguales");
    }
    
    public void ValidarIdSemantico()
    {
        boolean banTabla = false;
        if(!tablaSimbolos.isEmpty())
            for(String i : tablaSimbolos.keySet())
                if(temporalValor.equals(i))
                    banTabla = true;
        if(banTabla)
        {
            valorExprecion = temporalValor;
            datoExprecion = tablaSimbolos.get(valorExprecion);
        }
        else
            this.Error("Semantico", saltoLinea, "No puedes realizar una asignacion con una variable no declarada");
    }
    
    public void AnalizadorSemantico(String token)
    {
        boolean banId = false;
        if(token.equals("id"))
        {
            for(String id : tablaSimbolos.keySet())
                if(temporalValor.equals(id))
                    banId = true;
            if(banId)
                pilaSemantica.push(tablaSimbolos.get(temporalValor));
            else
                this.Error("Semantico", saltoLinea, "No puedes realizar una exprecion con una variable no declarada");
        }
        if(token.equals("num"))
            pilaSemantica.push(tipoDato);
        if(token.equals("+") || token.equals("-"))
            this.OperadoresPila(0);
        if(token.equals("*") || token.equals("/"))
            this.OperadoresPila(1);
        if(token.equals("("))
            this.OperadoresPila(2);
        if(token.equals(")"))
            this.OperadoresPila(3);
    }
    
    public void OperadoresPila(int valor)
    {
        if(pilaOperadores.isEmpty())
            pilaOperadores.push(valor);
        else
        {
            switch(valor)
            {
                case 2 -> pilaOperadores.push(valor);
                case 3 ->
                {
                    for(int i : pilaOperadores)
                        if(i == 2)
                        {
                            pilaOperadores.pop();
                            break;
                        }
                        else
                            this.TiposDatosAnalisis(pilaOperadores.pop());
                }
                default ->
                {
                    do
                    {
                        if(pilaOperadores.peek() <= valor)
                            this.TiposDatosAnalisis(pilaOperadores.pop());
                        else
                            break;
                    }while(!pilaOperadores.isEmpty());
                    pilaOperadores.push(valor);
                }
            }
        }
    }
    
    public void TiposDatosAnalisis(int operador)
    {
        int valor1, valor2, resultado;
        valor1 = pilaSemantica.pop();
        valor2 = pilaSemantica.pop();
        resultado = tablaSemantica[valor1][valor2];
        if(resultado != -1)
        {
            pilaSemantica.push(resultado);
            if(pilaOperadores.isEmpty())
                this.AnalisisExprecion();
        }
        else
            this.Error("Semantico", saltoLinea, "No se puede realizar una exprecion con estos tipos de datos");
        AnalisisSemantico.append(codigoIntermedioSalida);
    }
    
    public void AnalisisExprecion()
    {
        if(!pilaOperadores.isEmpty())
            this.TiposDatosAnalisis(pilaOperadores.pop());
        else
            if(datoExprecion != pilaSemantica.pop())
                this.Error("Semantico", saltoLinea, "No se pueden igualar distintos tipos de datos");
    }
    
    public void Error(String tipoError, int NumeroLinea, String token)
    {
        switch(tipoError)
        {
            case "Lexico" -> vistaError.append("Error " + tipoError + " en linea " + NumeroLinea + "\n");
            case "Sintactico" ->
            {
                vistaError.append("Error " + tipoError + " en linea " + NumeroLinea);
                this.AnalizarTokenEsperado(token);
                vistaAnalizadorSintactico.append("Se Rechaza");
            }
            case "Semantico" ->
            {
                vistaError.append("Error " + tipoError + " en linea " + NumeroLinea + ": " + token);
                vistaAnalizadorSintactico.append("Se Rechaza");
            }
        }
        error = true;
    }
    
    public void AnalizarTokenEsperado(String token)
    {
        for(int x = 0; x < 15; x++)
            if(!tablaSintactica[Integer.parseInt(estadoAnterior)][x].equals("-1"))
                tokenEsperados.addElement(terminalesNoterminaleSintactico[x]);
        
        vistaError.append(" se recivio " + token + " Se esperaba " + String.valueOf(tokenEsperados));
    }
    
    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
                
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new interfaz().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea AnalisisSemantico;
    private javax.swing.JButton correr;
    private javax.swing.JScrollPane inRaiz;
    private javax.swing.JTextArea inTexto;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea vistaAnalizadorLexico;
    private javax.swing.JTextArea vistaAnalizadorSintactico;
    private javax.swing.JTextArea vistaError;
    // End of variables declaration//GEN-END:variables
}
