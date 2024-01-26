import request from '@/utils/request'

const api_name = '/admin/core/lend'

export default {
  // 标的列表
  getList() {
    return request({
      url: `${api_name}/list`,
      method: 'get'
    })
  },

  // 获取标的信息
  show(id) {
    return request({
      url: `${api_name}/show/${id}`,
      method: 'get'
    })
  },

  // 放款
  makeLoan(id) {
    return request({
      url: `${api_name}/makeLoan/${id}`,
      method: 'post'
    })
  }
}
