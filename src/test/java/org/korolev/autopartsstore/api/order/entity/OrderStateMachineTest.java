package org.korolev.autopartsstore.api.order.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderStateMachineTest {

    @Test
    void pending_canTransitionToProcessingAndCanceled() {
        assertThat(OrderStatus.PENDING.canTransitionTo(OrderStatus.PROCESSING)).isTrue();
        assertThat(OrderStatus.PENDING.canTransitionTo(OrderStatus.CANCELED)).isTrue();
    }

    @Test
    void pending_cannotTransitionToCompleted() {
        assertThat(OrderStatus.PENDING.canTransitionTo(OrderStatus.COMPLETED)).isFalse();
    }

    @Test
    void completed_canOnlyTransitionToReturned() {
        assertThat(OrderStatus.COMPLETED.canTransitionTo(OrderStatus.RETURNED)).isTrue();
        assertThat(OrderStatus.COMPLETED.canTransitionTo(OrderStatus.CANCELED)).isFalse();
        assertThat(OrderStatus.COMPLETED.canTransitionTo(OrderStatus.PENDING)).isFalse();
    }

    @Test
    void canceledAndReturnedAreTerminal() {
        assertThat(OrderStatus.CANCELED.canTransitionTo(OrderStatus.PROCESSING)).isFalse();
        assertThat(OrderStatus.RETURNED.canTransitionTo(OrderStatus.PENDING)).isFalse();
    }
}