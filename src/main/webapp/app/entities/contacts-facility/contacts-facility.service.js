(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('ContactsFacility', ContactsFacility);

    ContactsFacility.$inject = ['$resource'];

    function ContactsFacility ($resource) {
        var resourceUrl =  'api/contacts-facilities/:id';

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
            'contactsByFacility': {url: 'api/contacts-by-facility/:id', method: 'GET', isArray: true},
            'update': { method:'PUT' }
        });
    }
})();
