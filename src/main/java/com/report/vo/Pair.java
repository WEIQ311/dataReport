package com.report.vo;


import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.validation.constraints.NotNull;

/**
 * @author weiQiang
 */
public class Pair<L, R> {

    @NotNull(message = "左属性不能为空!")
    private final L left;
    @NotNull(message = "右属性不能为空!")
    private R right;

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public static <U, V> Pair<U, V> create(U u, V v) {
        return new Pair<U, V>(u, v);
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    public void setRight(R right) {
        this.right = right;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Pair)) {
            return false;
        }
        Pair<?, ?> that = (Pair<?, ?>) other;
        return new EqualsBuilder().append(this.left, that.left).append(this.right, that.right).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.left).append(this.right).toHashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        sb.append(null == left ? "" : left.toString());
        sb.append(",");
        sb.append(null == right ? "" : right.toString());
        sb.append(")");
        return sb.toString();
    }
}
