import customtkinter as ctk
from tkinter import messagebox

ctk.set_appearance_mode("light")
ctk.set_default_color_theme("blue")  # usa o tema base  


app = ctk.CTk()
app.title("Calculadora de TMB e TDEE - Nutricionista Juju")
app.geometry("500x700")

# Cor de fundo rosa mais forte
app.configure(fg_color="#ffb6c1")

# --- Fatores de atividade física ---
fatores_atividade = {
    "Sedentário (pouco ou nenhum exercício)": 1.2,
    "Levemente ativo (1-3 dias/semana)": 1.375,
    "Moderadamente ativo (3-5 dias/semana)": 1.55,
    "Muito ativo (6-7 dias/semana)": 1.725,
    "Extremamente ativo (treino pesado 2x/dia)": 1.9
}


def mostrar_resultado(tmb, fator):
    tdee = tmb * fator

    resultado_janela = ctk.CTkToplevel(app)
    resultado_janela.title("Resultado")
    resultado_janela.geometry("350x250")
    resultado_janela.configure(fg_color="#ffe6f0")  # rosa clarinho

    # Faz a janela ficar na frente
    resultado_janela.transient(app)
    resultado_janela.grab_set()

    # Centraliza a janela de resultado em relação à janela principal
    app.update_idletasks()
    x = app.winfo_x() + (app.winfo_width() // 2) - (350 // 2)
    y = app.winfo_y() + (app.winfo_height() // 2) - (250 // 2)
    resultado_janela.geometry(f"+{x}+{y}")

    titulo = ctk.CTkLabel(resultado_janela, text="Resultados:", font=("Arial", 18, "bold"), text_color="black")
    titulo.pack(pady=15)

    label_tmb = ctk.CTkLabel(resultado_janela, text=f"TMB: {tmb:.2f} kcal/dia", font=("Arial", 16), text_color="black")
    label_tmb.pack(pady=10)

    label_tdee = ctk.CTkLabel(resultado_janela, text=f"Gasto Total (TDEE): {tdee:.2f} kcal/dia", font=("Arial", 16), text_color="black")
    label_tdee.pack(pady=10)

    fechar_btn = ctk.CTkButton(
        resultado_janela, 
        text="Fechar", 
        command=resultado_janela.destroy,
        fg_color="#ffccd5",
        hover_color="#ff99aa",
        text_color="black",
        corner_radius=15
    )
    fechar_btn.pack(pady=10)


def calcular_tmb(formula):
    try:
        peso = float(entry_peso.get())
        altura = float(entry_altura.get())
        idade = int(entry_idade.get())
        sexo = sexo_var.get()
        fator = fatores_atividade[atividade_var.get()]

        if formula == "Harris":
            if sexo == "Homem":
                tmb = 66.5 + (13.75 * peso) + (5.003 * altura) - (6.75 * idade)
            else:
                tmb = 655.1 + (9.563 * peso) + (1.850 * altura) - (4.676 * idade)

        elif formula == "Mifflin":
            if sexo == "Homem":
                tmb = (10 * peso) + (6.25 * altura) - (5 * idade) + 5
            else:
                tmb = (10 * peso) + (6.25 * altura) - (5 * idade) - 161

        elif formula == "DRI":
            # Fórmulas DRI baseadas em peso/idade/sexo (simplificadas)
            if sexo == "Homem":
                if 18 <= idade <= 30:
                    tmb = 15.3 * peso + 679
                elif 31 <= idade <= 60:
                    tmb = 11.6 * peso + 879
                else:
                    tmb = 13.5 * peso + 487
            else:  # Mulher
                if 18 <= idade <= 30:
                    tmb = 14.7 * peso + 496
                elif 31 <= idade <= 60:
                    tmb = 8.7 * peso + 829
                else:
                    tmb = 10.5 * peso + 596

        mostrar_resultado(tmb, fator)

    except:
        messagebox.showerror("Erro", "Por favor, insira valores válidos.")


titulo = ctk.CTkLabel(app, text="", font=("Arial", 22, "bold"), text_color="black")
titulo.pack(pady=20)

subtitulo = ctk.CTkLabel(app, text="", font=("Arial", 16), text_color="black")
subtitulo.pack(pady=5)


frame_inputs = ctk.CTkFrame(app, corner_radius=15, fg_color="#ff99bb")  # rosa mais fechado
frame_inputs.pack(pady=20, padx=20, fill="both", expand=True)

label_peso = ctk.CTkLabel(frame_inputs, text="Peso (kg):", text_color="black")
label_peso.pack(pady=5)
entry_peso = ctk.CTkEntry(frame_inputs, placeholder_text="Ex: 70")
entry_peso.pack(pady=5)

label_altura = ctk.CTkLabel(frame_inputs, text="Altura (cm):", text_color="black")
label_altura.pack(pady=5)
entry_altura = ctk.CTkEntry(frame_inputs, placeholder_text="Ex: 170")
entry_altura.pack(pady=5)

label_idade = ctk.CTkLabel(frame_inputs, text="Idade:", text_color="black")
label_idade.pack(pady=5)
entry_idade = ctk.CTkEntry(frame_inputs, placeholder_text="Ex: 25")
entry_idade.pack(pady=5)


sexo_var = ctk.StringVar(value="Mulher")
label_sexo = ctk.CTkLabel(frame_inputs, text="Sexo:", text_color="black")
label_sexo.pack(pady=5)
radio_homem = ctk.CTkRadioButton(frame_inputs, text="Homem", variable=sexo_var, value="Homem")
radio_mulher = ctk.CTkRadioButton(frame_inputs, text="Mulher", variable=sexo_var, value="Mulher")
radio_homem.pack()
radio_mulher.pack()

atividade_var = ctk.StringVar(value=list(fatores_atividade.keys())[0])
label_atividade = ctk.CTkLabel(frame_inputs, text="Nível de atividade física:", text_color="black")
label_atividade.pack(pady=5)

atividade_menu = ctk.CTkOptionMenu(frame_inputs, variable=atividade_var, values=list(fatores_atividade.keys()))
atividade_menu.pack(pady=5)

btn_harris = ctk.CTkButton(frame_botoes, text="Harris-Benedict", command=lambda: calcular_tmb("Harris"),
                           corner_radius=20, fg_color="#ffccd5", hover_color="#ff99aa", text_color="black")
btn_harris.pack(pady=5, padx=10)

btn_mifflin = ctk.CTkButton(frame_botoes, text="Mifflin-St Jeor", command=lambda: calcular_tmb("Mifflin"),
                            corner_radius=20, fg_color="#ffccd5", hover_color="#ff99aa", text_color="black")
btn_mifflin.pack(pady=5, padx=10)

btn_dri = ctk.CTkButton(frame_botoes, text="DRI", command=lambda: calcular_tmb("DRI"),
                        corner_radius=20, fg_color="#ffccd5", hover_color="#ff99aa", text_color="black")
btn_dri.pack(pady=5, padx=10)

rodape = ctk.CTkLabel(app, text="Desenvolvido para a nutricionista Juliana Vitória Alves da Silva.", font=("Arial", 12), text_color="black")
rodape.pack(side="bottom", pady=10)

app.mainloop()
