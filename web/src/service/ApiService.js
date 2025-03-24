import axios from 'axios'

export const baseUrl = 'http://localhost:8080'

const config = {
  headers: {
    "Content-Type": "application/json"
  }
};

export class ApiService {
  async get(path) {
    const fullPath = `${baseUrl}/${path}`
    try {
      const result = await axios.get(fullPath, config)
      return result.data
    } catch (e) {
      console.log('Error during GET to: ' + path)
      console.error(e)
      return e
    }
  }

  async post(path, request) {
    const fullPath = `${baseUrl}/${path}`
    try {
      const result = await axios.post(fullPath, request, config)
      return result.data
    } catch (e) {
      console.log('Error during POST to: ' + path)
      console.error(e)
      return e
    }

    return null
  }

  patch(path, request) {
    return axios({
      method: 'patch',
      url: baseUrl + path,
      data: request,
      config: config
    })
  }

  delete(path, request) {
    return axios({
      method: 'delete',
      url: baseUrl + path,
      data: request,
      config: config
    })
  }
}
