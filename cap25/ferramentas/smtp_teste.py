"""Servidor SMTP de testes: mostra um resumo de cada e-mail recebido (porta 2525)."""
import asyncio
import email
import email.policy
import sys
from aiosmtpd.controller import Controller


class Resumo:
    async def handle_DATA(self, server, session, envelope):
        msg = email.message_from_bytes(envelope.content, policy=email.policy.default)
        print("=== e-mail recebido ===")
        print("De:      ", msg["From"])
        print("Para:    ", ", ".join(envelope.rcpt_tos))
        print("Assunto: ", msg["Subject"])
        print("Partes:")
        for parte in msg.walk():
            if parte.is_multipart():
                print("  ", parte.get_content_type())
                continue
            dados = parte.get_payload(decode=True) or b""
            nome = parte.get_filename()
            print("     -", parte.get_content_type(), f"({len(dados)} bytes)",
                  f"anexo: {nome}" if nome else "")
        sys.stdout.flush()
        return "250 Message accepted for delivery"


controller = Controller(Resumo(), hostname="localhost", port=2525)
controller.start()
print("Servidor SMTP de testes em localhost:2525", flush=True)
try:
    asyncio.new_event_loop().run_forever()
except KeyboardInterrupt:
    pass
