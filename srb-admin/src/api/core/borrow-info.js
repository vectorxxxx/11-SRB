import request from '@/utils/request'

const api_anme = '/admin/core/borrowInfo'
export default {
  // 获取借款列表
  getList() {
    return request({
      url: `${api_anme}/list`,
      method: 'get'
    })
  },

  // 获取借款详情
  show(id) {
    return request({
      url: `${api_anme}/show/${id}`,
      method: 'get'
    })
  },

  // 审批借款信息
  approval(borrowInfoApprovalVO) {
    return request({
      url: `${api_anme}/approval`,
      method: 'post',
      data: borrowInfoApprovalVO
    })
  }
}
