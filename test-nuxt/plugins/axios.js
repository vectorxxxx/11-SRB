export default function ({$axios, redirect}) {
    $axios.onRequest(config => {
        console.log('Marking request to ' + config.url)
    })

    $axios.onRequest(response =>{
        console.log('Marking response')
        return response
    })

    $axios.onError(err => {
        console.log(err)
    })
}
