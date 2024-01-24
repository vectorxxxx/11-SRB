import request from '@/utils/request'

const api_name = '/admin/core/userInfo'

export default {
  // 获取用户分页列表
  getPageList(page, limit, searchObj) {
    return request({
      url: `${api_name}/list/${page}/${limit}`,
      method: 'get',
      params: searchObj
    })
  },
  // 锁定和解锁
  lock(id, status) {
    return request({
      url: `${api_name}/lock/${id}/${status}`,
      method: 'put'
    })
  },
  // 用户日志列表
  getUserLoginRecord(userId) {
    return request({
      url: `/admin/core/userLoginRecord/listTop50/${userId}`,
      method: 'get'
    })
  }
}
