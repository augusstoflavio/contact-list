package br.com.mesainc.contacts.domain

class GetContactsUseCase(private val contactRepository: ContactRepository) {

    suspend operator fun invoke(): Result<List<Contact>> {
        return contactRepository.getContacts()
    }
}