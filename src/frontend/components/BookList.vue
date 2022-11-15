<template>
    <div class="list row">
      <div class="col-md-8">
        <div class="input-group mb-3">
          <input
            type="text"
            class="form-control"
            placeholder="Search by title"
            v-model="title"
          />
          <div class="input-group-append">
            <button
              class="btn btn-outline-secondary"
              type="button"
              @click="searchTitle">
              Search
            </button>
          </div>
        </div>
      </div>
      <div class="col-md-6">
        <h4>Tutorials List</h4>
        <ul class="list-group">
          <li
            class="list-group-item"
            :class="{ active: index == currentIndex }"
            v-for="(book, index) in books"
            :key="index"
            @click="setActiveBook(book, index)"
          >
            {{ book.title }}
          </li>
        </ul>
  
        <button class="m-3 btn btn-sm btn-danger" @click="removeAllBooks">
          Remove All
        </button>
      </div>
      <div class="col-md-6">
        <div v-if="currentBook.id">
          <h4>Book</h4>
          <div>
            <label><strong>Title:</strong></label> {{ currentBook.title }}
          </div>
          <div>
            <label><strong>Description:</strong></label>
            {{ currentBook.description }}
          </div>
          <div>
            <label><strong>Status:</strong></label>
            {{ currentBook.published ? "Published" : "Pending" }}
          </div>
  
          <router-link
            :to="'/tutorials/' + currentBook.id"
            class="badge badge-warning"
            >Edit</router-link
          >
        </div>
        <div v-else>
          <br />
          <p>Please click on a Tutorial...</p>
        </div>
      </div>
    </div>
</template>
  
<script lang="ts">
    import { defineComponent } from "vue";
    import BookService from "@/services/BookService";
    import Book from "@/types/Book"
    import BookResponse from "@/types/BookResponse";
  
    export default defineComponent({
        name: "books-list",
        data() {
            return {
                books: [] as Book[],
                currentBook: {} as Book,
                currentIndex: -1,
                title: "",
            };
        },
        methods: {
            retrieveBooks() {
                BookService.getAll()
                    .then((response: BookResponse) => {
                        this.books = response.data;
                        console.log(response.data);
                    })
                    .catch((e: Error) => {
                        console.log(e);
                    });
            },
  
            refreshList() {
                this.retrieveBooks();
                this.currentBook = {} as Book;
                this.currentIndex = -1;
            },
  
            setActiveBook(book: Book, index = -1) {
                this.currentBook = book;
                this.currentIndex = index;
            },
  
            removeAllBooks() {
                BookService.deleteAll()
                    .then((response: BookResponse) => {
                        console.log(response.data);
                        this.refreshList();
                    })
                    .catch((e: Error) => {
                        console.log(e);
                });
            },
  
            searchTitle() {
                BookService.findByTitle(this.title)
                    .then((response: BookResponse) => {
                        this.books = response.data;
                        this.setActiveBook({} as Book);
                        console.log(response.data);
                    })
                    .catch((e: Error) => {
                        console.log(e);
                    });
            },
        },
        mounted() {
            this.retrieveBooks();
        },
    });
</script>
  
<style>
    .list {
        text-align: left;
        max-width: 750px;
        margin: auto;
    }
</style>