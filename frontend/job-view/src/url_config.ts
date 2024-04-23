const API_HTTP_URL = process.env.REACT_APP_API_HTTP
const API_WEBSOCKET_URL = process.env.REACT_APP_API_WEBSOCKET

export const Endpoints = {
    websocket:{
        base:`${API_WEBSOCKET_URL}`,
        jobStatus:`${API_WEBSOCKET_URL}/jobs/status`
    },
    http:{
        base:API_HTTP_URL,
        jobs:`${API_HTTP_URL}/jobs`
    }
};

