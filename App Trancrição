import customtkinter as ctk
from vosk import Model, KaldiRecognizer
import pyaudio
import json
import threading
from PIL import Image, ImageTk, ImageOps
import os
import webbrowser
import urllib.parse

ctk.set_appearance_mode("light")
ctk.set_default_color_theme("blue")


class SpeechApp(ctk.CTk):
    def __init__(self):
        super().__init__()

        self.title("TalkFree 3.0")
        self.geometry("600x600")
        self.resizable(False, False)

        self.logo_light = ctk.CTkImage(Image.open("logo_light.png"), size=(100, 100))
        self.logo_dark = ctk.CTkImage(Image.open("logo_dark.png"), size=(100, 100))

        self.logo_label = ctk.CTkLabel(self, image=self.logo_light, text="")
        self.logo_label.pack(pady=(20, 5))

        self.theme_switch = ctk.CTkSwitch(self, text="Tema", command=self.toggle_theme)
        self.theme_switch.pack(pady=10)

        self.tabview = ctk.CTkTabview(self, width=560, height=450)
        self.tabview.pack(padx=20, pady=10, fill="both", expand=True)

        # ----- Aba de Transcrição -----
        self.tabview.add("Transcrição")
        self.textbox = ctk.CTkTextbox(self.tabview.tab("Transcrição"), height=250, font=("Arial", 14), wrap="word")
        self.textbox.pack(padx=20, pady=10, fill="both", expand=False)

        self.clear_button = ctk.CTkButton(self.tabview.tab("Transcrição"), text="✕", width=30, height=30,
                                          command=self.clear_text, fg_color="#D3D3D3", text_color="black")
        self.clear_button.place(x=500, y=310)

        self.listen_button = ctk.CTkButton(self.tabview.tab("Transcrição"), text="Iniciar Transcrição",
                                           command=self.toggle_listening)
        self.listen_button.pack(pady=10)

        # ----- Aba Avatar Libras -----
        self.tabview.add("Avatar Libras")
        try:
            avatar_img2 = Image.open("avatar.png")
            avatar_img2 = ImageOps.mirror(avatar_img2)
            avatar_img2 = avatar_img2.resize((300, 300), Image.ANTIALIAS)
            self.avatar_image2 = ctk.CTkImage(light_image=avatar_img2, dark_image=avatar_img2)
            self.avatar_label2 = ctk.CTkLabel(self.tabview.tab("Avatar Libras"), image=self.avatar_image2, text="")
            self.avatar_label2.pack(pady=20)
        except Exception as e:
            self.avatar_label2 = ctk.CTkLabel(self.tabview.tab("Avatar Libras"), text="Imagem avatar.png não encontrada.")
            self.avatar_label2.pack(pady=20)

        self.btn_libras = ctk.CTkButton(self.tabview.tab("Avatar Libras"), text="Traduzir para Libras",
                                        command=self.abrir_libras)
        self.btn_libras.pack(pady=10)

        self.listening = False
        self.model = Model("model")  # A pasta 'model' precisa estar no mesmo diretório
        self.recognizer = KaldiRecognizer(self.model, 16000)
        self.audio = pyaudio.PyAudio()

    def toggle_theme(self):
        if self.theme_switch.get() == 1:
            ctk.set_appearance_mode("dark")
            self.logo_label.configure(image=self.logo_dark)
            self.clear_button.configure(fg_color="#3A3B3C", text_color="white")
        else:
            ctk.set_appearance_mode("light")
            self.logo_label.configure(image=self.logo_light)
            self.clear_button.configure(fg_color="#D3D3D3", text_color="black")

    def clear_text(self):
        self.textbox.delete("1.0", "end")

    def toggle_listening(self):
        if not self.listening:
            self.listening = True
            self.listen_button.configure(text="Parar Transcrição")
            threading.Thread(target=self.listen_microphone, daemon=True).start()
        else:
            self.listening = False
