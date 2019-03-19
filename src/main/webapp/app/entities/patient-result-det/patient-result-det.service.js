(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('PatientResultDet', PatientResultDet);

    PatientResultDet.$inject = ['$resource'];

    function PatientResultDet ($resource) {
        var resourceUrl =  'api/patient-result-dets/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
