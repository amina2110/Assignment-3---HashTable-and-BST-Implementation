package com.company;

public class BST <K extends Comparable<K>, V>{
    private Node root;
    public int size = 0;

    private class Node{
        private K key;
        private V val;
        private Node left, right;
        public Node(K key, V val){
            this.key = key;
            this.val = val;
        }
    }

    public void put(K key, V val){
        Node newNode = new Node(key, val);

        root = putRecursion(root, newNode);

        size++;
    }

    public Node putRecursion(Node root, Node node){
        K key = node.key;
        if(root == null){
            root = node;
            return root;
        }
        else if(key.compareTo(root.key) < 0)
        {
            root.left = putRecursion(root.left, node);
        }
        else
        {
            root.right = putRecursion(root.right, node);
        }
        return root;
    }

    public void display(){
        displayRecursion(root);
    }

    private void displayRecursion(Node root){
        if(root != null){
            displayRecursion(root.left);
            System.out.println(root.key+"_"+root.val);
            displayRecursion(root.right);
        }
    }

    public V get(K key){
        return getRecursion(root, key);
    }

    private V getRecursion(Node root, K key){
        if(root == null){
            return null;
        }
        else if(root.key.compareTo(key) == 0){
            return root.val;
        }
        else if(root.key.compareTo(key) > 0){
            return getRecursion(root.left, key);
        }
        else {
            return getRecursion(root.right, key);
        }
    }

    public void delete(K key){

        if(get(key) != null){
            deleteRecursion(root, key);
        }
        else {
            System.out.println("There's no "+key.getClass().getSimpleName()+": "+key);
        }
        size--;
    }

    public Node deleteRecursion(Node root, K key){
        if(root == null){
            return root;
        }
        else if(key.compareTo(root.key) < 0){
            root.left = deleteRecursion(root.left, key);
        }
        else if(key.compareTo(root.key) > 0){
            root.right = deleteRecursion(root.right, key);
        }
        else {
            if(root.left == null && root.right == null){
                root = null;
            }
            else if(root.right != null){
                root.key = successor(root).key;
                root.right = deleteRecursion(root.right, root.key);
            }
            else{
                root.key = predecessor(root).key;
                root.left = deleteRecursion(root.left, root.key);
            }

        }
        return root;
    }

    private Node successor(Node root){
        root = root.right;
        while (root.left != null){
            root = root.left;
        }
        return new Node(root.key, root.val);
    }

    private Node predecessor(Node root){
        root = root.left;
        while (root.right != null){
            root = root.right;
        }
        return new Node(root.key, root.val);
    }
}
