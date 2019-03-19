(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('EvaluationSignature', EvaluationSignature);

    EvaluationSignature.$inject = ['$resource'];

    function EvaluationSignature ($resource) {
        var resourceUrl =  'api/evaluation-signatures/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
            'evaluationSignaturesByEvaluation': {url: 'api/evaluation-signatures-by-evaluation/:id', method: 'GET', isArray: true},
            'getEvaluationSignatureVO': {url: 'api/evaluation-signatures/evaluation/:id', method: 'GET'}
        });
    }
})();
