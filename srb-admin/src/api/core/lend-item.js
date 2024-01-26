import request from '@/utils/request'

const api_name = '/admin/core/lendItem'

export default {
  getList(lendId) {
    return request({
      url: `${api_name}/list/${lendId}`,
      method: 'get'
    })
  }
}
