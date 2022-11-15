<template>
    <div v-if="currentBook.id" class="edit-form">
      <h4>Book</h4>
      <form>
        <div class="form-group">
          <label for="title">Title</label>
          <input
            type="text"
            class="form-control"
            id="title"
            v-model="currentBook.title"
          />
        </div>
        <div class="form-group">
          <label for="description">Description</label>
          <input
            type="text"
            class="form-control"
            id="description"
            v-model="currentBook.description"
          />
        </div>
  
        <div class="form-group">
          <label><strong>Status:</strong></label>
          {{ currentBook.published ? "Published" : "Pending" }}
        </div>
      </form>
  
      <button
        class="badge badge-primary mr-2"
        v-if="currentBook.published"
        @click="updatePublished(false)">
        UnPublish
      </button>
      <button
        v-else
        class="badge badge-primary mr-2"
        @click="updatePublished(true)">
        Publish
      </button>
  
      <button class="badge badge-danger mr-2" @click="deleteBook">
        Delete
      </button>
  
      <button type="submit" class="badge badge-success" @click="updateBook">
        Update
      </button>
      <p>{{ message }}</p>
    </div>
  
    <div v-else>
      <br />
      <p>Please click on a Book...</p>
    </div>
  </template>
  
<script lang="ts">
    import { defineComponent } from "vue";
    import BookDataService from "@/services/BookService";
    import Book from "@/types/Book";
    import ResponseData from "@/types/Book";
  
  export default defineComponent({
    name: "Book",
    data() {
      return {
        currentBook: {} as Book,
        message: "",
      };
    },
    methods: {
      getBook(id: any) {
        BookDataService.get(id)
          .then((response: ResponseData) => {
            this.currentBook = response.data;
            console.log(response.data);
          })
          .catch((e: Error) => {
            console.log(e);
          });
      },
  
      updatePublished(status: boolean) {
        let data = {
          id: this.currentBook.id,
          title: this.currentBook.title,
          description: this.currentBook.description,
          published: status,
        };
  
        BookDataService.update(this.currentBook.id, data)
          .then((response: ResponseData) => {
            console.log(response.data);
            this.currentBook.published = status;
            this.message = "The status was updated successfully!";
          })
          .catch((e: Error) => {
            console.log(e);
          });
      },
  
      updateBook() {
        BookService.update(this.currentBook.id, this.currentBook)
          .then((response: ResponseData) => {
            console.log(response.data);
            this.message = "The Book was updated successfully!";
          })
          .catch((e: Error) => {
            console.log(e);
          });
      },
  
      deleteBook() {
        BookDataService.delete(this.currentBook.id)
          .then((response: ResponseData) => {
            console.log(response.data);
            this.$router.push({ name: "Books" });
          })
          .catch((e: Error) => {
            console.log(e);
          });
      },
    },
    mounted() {
      this.message = "";
      this.getBook(this.$route.params.id);
    },
  });
  </script>
  
  <style>
  .edit-form {
    max-width: 300px;
    margin: auto;
  }
  </style>