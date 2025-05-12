import axios from 'axios'

export const baseUrl = 'http://localhost:8080'

const config = {
  headers: {
    "Content-Type": "application/json"
  }
};

export class ApiService {
  async get(path) {
    const result = await axios.get(this.getPath(path), config)
    return result.data
  }

  async post(path, request) {
    const result = await axios.post(this.getPath(path), request, config)
    return result.data
  }

  async put(path, request) {
    await axios.put(this.getPath(path), request, config)
  }

  async delete(path, request) {
    await axios.delete(this.getPath(path), request, config)
  }

  getPath(path) {
    return `${baseUrl}/${path}`
  }
}
