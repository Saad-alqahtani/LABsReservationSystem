package sem451;

import java.util.HashMap;
import java.util.Map;

public class ReserveBlockList {
    private Map<String, Person> blockedPeople;

    public ReserveBlockList() {
        blockedPeople = new HashMap<>();
    }

    public boolean addBlockedPerson(Person person) {
        if (person == null || blockedPeople.containsKey(person.getId())) {
            return false;
        }
        person.setBlocked(true);
        blockedPeople.put(person.getId(), person);
        return true;
    }

    public boolean removeBlockedPerson(String id) {
        if (!blockedPeople.containsKey(id)) {
            return false;
        }
        blockedPeople.remove(id).setBlocked(false);
        return true;
    }

    public boolean isPersonBlocked(String id) {
        return blockedPeople.containsKey(id);
    }

    public Person getBlockedPerson(String id) {
        return blockedPeople.get(id);
    }

    public void printBlockedPeople() {
        for (Person p : blockedPeople.values()) {
            System.out.println(p);
        }
    }

    public int countBlockedPeople() {
        return blockedPeople.size();
    }

    public void clearAllBlockedPeople() {
        for (Person p : blockedPeople.values()) {
            p.setBlocked(false);
        }
        blockedPeople.clear();
    }
}
