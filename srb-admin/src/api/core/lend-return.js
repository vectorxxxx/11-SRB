import request from '@/utils/request'

const api_name = '/admin/core/lendReturn'

export default {
  getList(lendId) {
    return request({
      url: `${api_name}/list/${lendId}`,
      method: 'get'
    })
  }
}
