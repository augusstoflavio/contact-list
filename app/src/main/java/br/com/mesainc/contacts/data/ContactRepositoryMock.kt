package br.com.mesainc.contacts.data

import br.com.mesainc.contacts.domain.Contact
import br.com.mesainc.contacts.domain.ContactRepository
import br.com.mesainc.contacts.domain.Result

class ContactRepositoryMock : ContactRepository {
    override suspend fun getContacts(): Result<List<Contact>> {
        return Result.Success(
            listOf(
                Contact(
                    name = "Henrique Luan Lucca Aragão",
                    email = "henrique.luan.aragao@oana.com.br",
                    telephone = "(66) 2107-7967"

                ),
                Contact(
                    name = "Isabella Helena Corte Real",
                    email = "isabella-cortereal74@senhorasdaarte.com.br",
                    telephone = "(21) 2717-3651"

                ),
                Contact(
                    name = "Lara Joana Freitas",
                    email = "larajoanafreitas@paraisopolis.com.br",
                    telephone = "(73) 3515-2273"

                ),
                Contact(
                    name = "Fabiana Luzia Araújo",
                    email = "fabiana.luzia.araujo@uninet.com.br",
                    telephone = "(89) 3887-9279"

                ),
                Contact(
                    name = "Paulo Carlos Eduardo Lorenzo Souza",
                    email = "paulocarlossouza@mectron.com.br",
                    telephone = "(99) 2683-5418"

                ),
                Contact(
                    name = "Bento Rodrigo Kaique Araújo",
                    email = "bentorodrigoaraujo@alstom.com",
                    telephone = "(61) 3947-7886"

                ),
                Contact(
                    name = "Renata Olivia Tânia Lima",
                    email = "renata-lima81@yahooo.com.br",
                    telephone = "(82) 2634-5547"

                ),
                Contact(
                    name = "Hugo José da Silva",
                    email = "hugo_dasilva@ortovip.com.br",
                    telephone = "(95) 2426-3527"

                ),
                Contact(
                    name = "Esther Sara Benedita da Conceição",
                    email = "esther_daconceicao@unink.com.br",
                    telephone = "(67) 3273-8161"

                ),
                Contact(
                    name = "Márcia Débora Márcia Melo",
                    email = "marciadeboramelo@eanac.com.br",
                    telephone = "(67) 2855-7116"

                ),
                Contact(
                    name = "Valentina Rosângela Pereira",
                    email = "valentina_pereira@andradecamara.com.br",
                    telephone = "(79) 3856-9763"

                )
            )
        )
    }
}