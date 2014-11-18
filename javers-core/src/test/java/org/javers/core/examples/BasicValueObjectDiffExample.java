package org.javers.core.examples;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.core.examples.model.Address;
import org.junit.Test;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author bartosz walacik
 */
public class BasicValueObjectDiffExample {

    @Test
    public void shouldCompareTwoObjects() {

        //given
        Javers javers = JaversBuilder.javers().build();

        Address a1 = new Address("New York","5th Avenue");
        Address a2 = new Address("New York","6th Avenue");

        //when
        Diff diff = javers.compare(a1, a2);

        //then
        //there should be one change of type {@link ValueChange}
        ValueChange change =  (ValueChange) diff.getChanges().get(0);

        assertThat(diff.getChanges()).hasSize(1);
        assertThat(change.getAffectedCdoId().value()).isEqualTo("org.javers.core.examples.model.Address/");
        assertThat(change.getProperty().getName()).isEqualTo("street");
        assertThat(change.getLeft()).isEqualTo("5th Avenue");
        assertThat(change.getRight()).isEqualTo("6th Avenue");

        System.out.println("diff: " + javers.toJson(diff));
    }
}
