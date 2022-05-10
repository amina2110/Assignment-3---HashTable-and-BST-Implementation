package com.company;

public class MyHashtable<K,V> {
    public class HashNode<K,V>{
        private K key;
        private V value;
        private HashNode<K,V> next;

        public HashNode(K key,V value){
            this.key = key;
            this.value = value;
        }

        public String toString(){
            return "{"+key+" "+value+"}";
        }
    }

    private HashNode<K,V>[] chainArray;
    private int M = 11;
    private float loadFactor = 0.75F;
    public int size;

    public MyHashtable(){
        chainArray = new HashNode[M];
    }

    public MyHashtable(int M){
        int capacity = (int)(M*loadFactor);
        if(capacity < 1){
            chainArray = new HashNode[capacity];
        }
    }

    public int hash(K key){
        return key.hashCode() & 0x7fffffff;
    }

    public void put(K key, V value){
        int index = getIndex(key);
        HashNode<K,V> newNode = new HashNode<>(key,value);
        if (chainArray[index] != null) {
            newNode.next = chainArray[index];
        }
        chainArray[index] = newNode;
        size++;

        if((1.0*size/M) >= loadFactor){
            increaseCapacity();
        }
    }

    private int getIndex(K key)
    {
        int hashCode = hash(key);
        int index = hashCode % M;

        if(index<0){
            index*=(-1);
        }
        return index;
    }

    public V get(K key){
        int index = getIndex(key);
        int hashCode = hash(key);

        HashNode<K, V> head = chainArray[index];

        while (head != null) {
            if (head.key.equals(key) && hash(head.key) == hashCode){
                return head.value;
            }

            head = head.next;

        }
        return null;
    }


    public V remove(K key)
    {
        int index = getIndex(key);
        int hashCode = hash(key);

        HashNode<K, V> head = chainArray[index];
        HashNode<K, V> prev = null;

        while (head != null) {
            if (head.key.equals(key) && hashCode == hash(head.key)) {
                break;
            }
            prev = head;
            head = head.next;
        }

        if (head == null)
            return null;

        size--;


        if (prev != null)
            prev.next = head.next;
        else
            chainArray[index] = head.next;


        return head.value;
    }


    public boolean contains(V value){
        boolean flag = false;
        for(int i=0; i<chainArray.length; i++){
            HashNode<K, V> newNode = chainArray[0];
            while (newNode.next != null){
                if(newNode.value.equals(value)){
                    flag = true;
                }
                newNode = newNode.next;
            }
        }
        return flag;
    }

    public K getKey(V value){
        K key = null;
        for(int i=0; i<chainArray.length; i++){
            HashNode<K, V> newNode = chainArray[0];
            while (newNode.next != null){
                if(newNode.value.equals(value)){
                    key = newNode.key;
                }
                newNode = newNode.next;
            }
        }
        return key;
    }

    public void increaseCapacity(){
        M = (int)(M*1.5);
        HashNode<K,V>[] newChainArray = new HashNode[M];
        System.arraycopy(chainArray, 0, newChainArray, 0, chainArray.length);
        chainArray = newChainArray;
    }
}
