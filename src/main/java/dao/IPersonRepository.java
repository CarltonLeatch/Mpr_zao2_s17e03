package dao;

import java.util.List;

import domain.Person;

public interface IPersonRepository extends IRepository<Person> {

	public List<Person> withName(String name);
}
