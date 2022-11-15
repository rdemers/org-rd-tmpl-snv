import http from "@/http-common";

class BookService {
  getAll(): Promise<any> {
    return http.get("/books");
  }

  get(id: any): Promise<any> {
    return http.get("/books/${id}");
  }

  create(data: any): Promise<any> {
    return http.post("/books", data);
  }

  update(id: any, data: any): Promise<any> {
    return http.put("/books/${id}", data);
  }

  delete(id: any): Promise<any> {
    return http.delete("/books/${id}");
  }

  deleteAll(): Promise<any> {
    return http.delete("/books");
  }

  findByTitle(title: string): Promise<any> {
    return http.get("/tutorials?title=${title}");
  }
}

export default new BookService();