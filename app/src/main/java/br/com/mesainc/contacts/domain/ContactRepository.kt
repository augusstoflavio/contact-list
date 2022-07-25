package br.com.mesainc.contacts.domain

interface ContactRepository {

    suspend fun getContacts(): Result<List<Contact>>
}