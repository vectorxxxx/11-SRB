import request from '@/utils/request'

const api_name = '/admin/core/borrower'
export default {
  // 获取借款人分页列表
  getPageList(page, limit, keyword) {
    return request({
      url: `${api_name}/list/${page}/${limit}`,
      method: 'get',
      params: keyword
    })
  },

  // 获取借款人信息
  show(id) {
    return request({
      url: `${api_name}/show/${id}`,
      method: 'get'
    })
  },

  // 审批
  approval(borrowerApprovalVO) {
    return request({
      url: `${api_name}/approval`,
      method: 'post',
      data: borrowerApprovalVO
    })
  }
}
