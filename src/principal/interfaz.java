package principal;

import java.util.Stack;

public class interfaz extends javax.swing.JFrame {

    NumeroLinea nl;
    String lexicoAlfabeto = "0123456789+-/*=(),;. \nABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz", cadenaEntrada, temporalId;
    String palabrasReservadas[] = {"int","float","char"}, terminalesNoterminaleSintactico[] = {"id","num","int","float","chart",",",";","+","-","*","/","(",")","=","$","P","Tipo","V","A","Exp","E","Term","T","F"};
    Stack<String> pilaSintactica = new Stack<>();
    int estado = 0, saltoLinea = 1, estadoSintactico = 0, tokenEntrada = 0;
    int tablaLexico[][] = {
        { 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 6, 6, 6, 6, 6, 6, 6, 6,-1, 0, 0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5},
        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 6, 6, 6,-1, 6, 6,-1, 6, 2, 0, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
        { 2, 3, 3, 3, 3, 3, 3, 3, 3, 3,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
        { 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 6, 6, 6, 6,-1, 6, 6,-1, 6,-1, 0, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 6, 6, 6, 6,-1, 6, 6,-1, 6, 2, 0, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
        { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6,-1, 0, 0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5},
        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 0, 0,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}
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
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        inTexto.setColumns(20);
        inTexto.setRows(5);
        inRaiz.setViewportView(inTexto);

        jButton1.setText("jButton1");

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(51, 51, 51))
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(inRaiz, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 197, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(inRaiz, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(32, 32, 32))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    public void IniciolizarVariables()
    {
        pilaSintactica.push("$");
        pilaSintactica.push("I0");
    }
    
    public void AnalizadorLexico()
    {
        int i, posicion;
        boolean banCadena = true;
        
        for(i = 0; i < cadenaEntrada.length(); i++)
        {
            posicion = lexicoAlfabeto.indexOf(cadenaEntrada.charAt(i));
            
            if(posicion == -1)
                this.Error();
            else
                estado = tablaLexico[estado][posicion];
            
            if(estado == -1)
                this.Error();
            
            if(estado == 5)
                if(banCadena)
                {
                    temporalId = cadenaEntrada.charAt(i) + "";
                    banCadena = false;
                }
                else
                    temporalId += cadenaEntrada.charAt(i);
            if(i + 1 < cadenaEntrada.length() && lexicoAlfabeto.indexOf(cadenaEntrada.charAt(i)) != -1 )
                if(tablaLexico[estado][lexicoAlfabeto.indexOf(cadenaEntrada.charAt(i +1))] == 0 || tablaLexico[estado][lexicoAlfabeto.indexOf(cadenaEntrada.charAt(i +1))] == 6)
                    this.ComprobarEstados(i);
                else
                    if(estado == 6)
                        this.ComprobarEstados(i);
            if(cadenaEntrada == "\n")
                saltoLinea++;
        }
    }
    
    public void ComprobarEstados(int i)
    {
        switch(estado)
        {
            case 1:
                this.EnviarToken("num");
                break;
            case 4:
                this.EnviarToken("num");
                break;
            case 3:
                this.EnviarToken("num");
                break;
            case 5:
                this.ComprobarId(i);
                break;
            case 6:
                this.EnviarToken(cadenaEntrada.charAt(i) + "");
                break;
        }
        
    }
    
    public void ComprobarId(int i)
    {
        boolean banId = false;
        
        for(String reservadas : palabrasReservadas)
            if(temporalId.compareTo(reservadas) == 0)
                banId = true;
        if(banId)
            this.EnviarToken(temporalId);
        else
            this.EnviarToken("id");
    }
    
    public void EnviarToken(String token)
    {
        this.AnalizadorSintactico(token);
    }
    
    public void AnalizadorSintactico(String token)
    {
        boolean banProduccion = false;
        
        do
        {
            if(pilaSintactica.peek().charAt(1) == 'I')
                this.EstadosSintacticos(token);
            else
                if(pilaSintactica.peek().charAt(1) == 'P' )
                    this.Producciones(pilaSintactica.peek());
                else
                    this.Error();
        }while(banProduccion);        
    }
    
    public void EstadosSintacticos(String token)
    {
        estadoSintactico = Integer.parseInt(pilaSintactica.peek().substring(1));
        for(tokenEntrada = 0; tokenEntrada < terminalesNoterminaleSintactico.length && terminalesNoterminaleSintactico[tokenEntrada].equals(token); tokenEntrada++);
        
        if(tablaSintactica[estadoSintactico][tokenEntrada].charAt(1) == 'I')
        {
            pilaSintactica.push(token);
            pilaSintactica.push(tablaSintactica[estadoSintactico][tokenEntrada]);
        }
        else
            if(tablaSintactica[estadoSintactico][tokenEntrada].charAt(1) == 'P')
                this.Producciones(tablaSintactica[estadoSintactico][tokenEntrada]);
            else
                this.Error();
    }
    
    public void Producciones(String produccion)
    {
        
    }
    
    public void Error()
    {
        
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
    private javax.swing.JScrollPane inRaiz;
    private javax.swing.JTextArea inTexto;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables
}
