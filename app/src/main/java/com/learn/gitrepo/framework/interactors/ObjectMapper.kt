package com.learn.gitrepo.framework.interactors

import com.learn.domain.BuiltBy
import com.learn.domain.GitRepo
import com.learn.gitrepo.framework.model.BuiltByEntity
import com.learn.gitrepo.framework.model.GitRepoEntity

class ObjectMapper {

    companion object {

        fun mapEntityToDomain(repoList: List<GitRepoEntity>): List<GitRepo> {
            return repoList.map {
                GitRepo(it.id,it.author, it.name ,it.avatar, it.url,
                    it.description, it.language, it.languageColor, it.stars,
                    it.forks, it.currentPeriodStars,
                    it.builtBy.map { builtBy -> BuiltBy(builtBy.username, builtBy.href, builtBy.avatar) }) }

        }

        fun mapDomainToEntity(repoList: List<GitRepo>) : List<GitRepoEntity> {
            return repoList.map {
                GitRepoEntity(it.id,it.author, it.name ,it.avatar, it.url,
                    it.description, it.language, it.languageColor, it.stars,
                    it.forks, it.currentPeriodStars,
                    it.builtBy.map { builtBy -> BuiltByEntity (builtBy.username, builtBy.href, builtBy.avatar) })}

        }

    }

}

