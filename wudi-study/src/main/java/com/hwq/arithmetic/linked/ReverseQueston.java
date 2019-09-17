package com.hwq.arithmetic.linked;

/**
 * @Auther: haowenqiang
 * @Description:链表反转
 */
public class ReverseQueston {
    //遍历
    private static ListNode reverse1(ListNode head) {
        ListNode prev, curr, temp;
        prev = null;
        curr = head;
        while (curr != null) {
            temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        return prev;
    }
    //递归
    private static ListNode reverse2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode temp = head.next;
        ListNode newNode = reverse2(temp);
        temp.next = head;
        head.next = null;
        return newNode;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(0);
        head.next=new ListNode(1);
        ListNode t = head.next;
        for (int i = 2; i <10 ; i++) {
            t.next = new ListNode(i);
            t = t.next;
        }
//        ListNode listNode = reverse1(head);
        ListNode listNode = reverse2(head);
        while (listNode!=null){
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
    }
}
