(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Contacts', Contacts);

    Contacts.$inject = ['$resource'];

    function Contacts ($resource) {
        var resourceUrl =  'api/contacts/:id';

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
            'contactsByRelationship': { url: 'api/contacts/relationship/:cid/:rid', method: 'GET', isArray: true },
            'guarantor': { url: 'api/contacts/guarantor/:id', method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
