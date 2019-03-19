/**
 * Created by PpTMUnited on 5/15/2017.
 */
(function () {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('SystemService', SystemService);

    SystemService.$inject = ['$resource'];

    function SystemService($resource) {
        var resourceUrl = 'system/:id';

        return $resource(resourceUrl, {}, {
            'attachConsent': {url: 'system/consent/attach-file', method: 'POST'},
            'deleteConsentFile': {url: 'api/files/:id', method: 'DELETE'},
            //evaluation file services
            'attachEvaluation': {url: 'system/evaluation/attach-file', method: 'POST'},
            'deleteEvaluationFile': {url: 'api/files/:id', method: 'DELETE'},
            //Shift file services
            'attachShiftFile': {url: 'system/shift/attach-file', method: 'POST'},
            'deleteShiftFile': {url: 'api/files/:id', method: 'DELETE'}
        });
    }
})();

